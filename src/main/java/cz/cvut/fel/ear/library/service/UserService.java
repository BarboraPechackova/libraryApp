package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.*;
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
    private final ReservationService reservationService;

    @Autowired
    public UserService(UserDao dao, RoleService roleService, BookDao bookDao, BookLoanDao bookLoanDao, ReservationService reservationService) {
        this.dao = dao;
        this.roleService = roleService;
        this.bookDao = bookDao;
        this.bookLoanDao = bookLoanDao;
        this.reservationService = reservationService;
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
        reservationService.deleteActiveUserReservations(user);
//        for (Reservation reservation : reservationService.getAllUserReservations(user)) {
//            reservationService.remove(reservation);
//        }
        for (Book book : bookDao.findAllFromUser(user)) {
            reservationService.deleteActiveBookReservations(book);
//            for (Reservation reservation : reservationService.getAllReservationsOfBook(book)) {
//                reservationService.remove(reservation);
//            }
            bookDao.remove(book);
        }
        if (user.getRoles()!=null) {
            for (Role role : user.getRoles()) {
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
