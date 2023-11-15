package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.ReservationDao;
import cz.cvut.fel.ear.library.exceptions.BookNotReturnedException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.enums.BookState;
import cz.cvut.fel.ear.library.model.enums.ReservationState;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Lombok;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Test
    public void addRoleToUserAddsRoleToUser() {}

    @Test
    public void removeRoleFromUserRemovesRoleFromUser() {}

    @Test
    public void removeUserRemovesUser() {
        final User u1 = new User();
        final User u2 = new User();
        em.persist(u1);
        em.persist(u2);

        assertEquals(2, userService.findAll().size());

        userService.removeUser(u1);

        assertEquals(1,userService.findAll().size());
    }

    @Test
    public void removeUserThatHasBorrowedBooksThrowsException() {
        final User user = new User();
        final BookLoan bookLoan = new BookLoan();
        final Book book = new Book();

        book.setName("kniha");
        bookLoan.setBook(book);
        bookLoan.setUser(user);
        bookLoan.setReturned(false);

        em.persist(user);
        em.persist(book);
        em.persist(bookLoan);

        assertThrows(BookNotReturnedException.class, () -> userService.removeUser(user));
    }

    @Test
    public void removeUserThatHasLentBooksThrowsException() {
        final User user = new User();
        final Book book = new Book();
        book.setUser(user);
        book.setState(BookState.VYPUJCENA);

        em.persist(user);
        em.persist(book);


        assertThrows(BookNotReturnedException.class, () -> userService.removeUser(user));
    }

    @Test
    public void removeUserRemovesAllTheirReservation() {
        final User user = new User();
        final Reservation reservation = new Reservation();
        final Book book = new Book();
        reservation.setUser(user);
        reservation.setBook(book);

        em.persist(user);
        em.persist(book);
        em.persist(reservation);

        assertEquals(1,reservationService.findAll().size());

        userService.removeUser(user);

        assertEquals(0,reservationService.findAll().size());
    }
}