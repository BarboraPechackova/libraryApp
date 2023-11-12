package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.*;
import cz.cvut.fel.ear.library.exceptions.BookAlreadyLoanedException;
import cz.cvut.fel.ear.library.exceptions.BookNotReturnedException;
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

    private final RoleService roleService;
    private final BookDao bookDao;
    private final BookLoanDao bookLoanDao;
    private final ReservationDao reservationDao;

    @Autowired
    public UserService(UserDao dao, RoleService roleService, BookDao bookDao, BookLoanDao bookLoanDao, ReservationDao reservationDao) {
        this.dao = dao;
        this.roleService = roleService;
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
//            for (Reservation reservation : reservationDao.getReservationsOfBook(book)) {
            for (Reservation reservation : reservationDao.getUserReservationsOfBook(user,book)) {
                reservationDao.remove(reservation);
            }
            bookDao.remove(book);
        }
        if (user.getRoles()!=null) {
            for (Role role : user.getRoles()) {
                // TODO: this can raise exception if user is the only admin
                roleService.removeRoleFromUser(user, role.getRole());
            }
        }
        dao.remove(user);
    }

    private void validateUserRemove(User user) {
        List<Book> userBooks = bookDao.findAllFromUser(user);
        for (Book book : userBooks) {
            if (book.getState() == BookState.VYPUJCENA) {
                throw new BookNotReturnedException("Remove user failed! Book " + book.getName() + " is still lent.");
            }
        }
        List<BookLoan> userLoans = bookLoanDao.getUserLoans(user);
        for (BookLoan bookLoan : userLoans) {
            if (!bookLoan.isReturned()) {
                throw new BookNotReturnedException("Remove user failed! Book " + bookLoan.getBook().getName() + " is not returned.");
            }
        }
    }
}
