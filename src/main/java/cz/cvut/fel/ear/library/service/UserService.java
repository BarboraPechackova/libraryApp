package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.*;
import cz.cvut.fel.ear.library.exceptions.BookIsAlreadyLoanedException;
import cz.cvut.fel.ear.library.exceptions.BookIsNotReturnedException;
import cz.cvut.fel.ear.library.model.*;
import cz.cvut.fel.ear.library.model.enums.BookState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserDao dao;

    private final RoleDao roleDao;
    private final BookDao bookDao;
    private final BookLoanDao bookLoanDao;
    private final ReservationDao reservationDao;

    @Autowired
    public UserService(UserDao dao, RoleDao roleDao, BookDao bookDao, BookLoanDao bookLoanDao, ReservationDao reservationDao) {
        this.dao = dao;
        this.roleDao = roleDao;
        this.bookDao = bookDao;
        this.bookLoanDao = bookLoanDao;
        this.reservationDao = reservationDao;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public User find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        dao.persist(user);
    }

    @Transactional
    public void removeUser(User user) {
        validateUserRemove(user);
        Objects.requireNonNull(user);

        for (Reservation reservation : reservationDao.getAllUserReservations(user)) {
            reservationDao.remove(reservation);
        }
        for (Book book : bookDao.findAllFromUser(user)) {
            for (Reservation reservation : reservationDao.getReservationsOfBook(book)) {
                reservationDao.remove(reservation);
            }
            bookDao.remove(book);
        }
        for (Role role : roleDao.findRolesOfUser(user)) {
            roleDao.remove(role);
        }
        dao.remove(user);
    }

    private void validateUserRemove(User user) {
        List<Book> userBooks = bookDao.findAllFromUser(user);
        for (Book book : userBooks) {
            if (book.getState() == BookState.VYPUJCENA) {
                throw new BookIsAlreadyLoanedException("Remove user failed! Book " + book.getName() + " is lent.");
            }
        }
        List<BookLoan> userLoans = bookLoanDao.getUserLoans(user);
        for (BookLoan bookLoan : userLoans) {
            if (!bookLoan.isReturned()) {
                throw new BookIsNotReturnedException("Remove user failed! Book " + bookLoan.getBook().getName() + " is not returned.");
            }
        }
    }
}
