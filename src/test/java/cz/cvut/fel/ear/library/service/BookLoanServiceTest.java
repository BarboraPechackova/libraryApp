package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.exceptions.BookLoanDatesException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.enums.BookState;
import cz.cvut.fel.ear.library.model.enums.ReservationState;
import cz.cvut.fel.ear.library.util.DateUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class BookLoanServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BookLoanService service;

    @Autowired
    private ReservationService reservationService;

    @Test
    void persistCorrectlyPersistsBookLoan() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        BookLoan loan = new BookLoan(DateUtils.getDateFromLocalDate(LocalDate.now()), DateUtils.getDateFromLocalDate(LocalDate.now().plusDays(7)), user, book);
        assertDoesNotThrow(() -> service.persist(loan));

        em.merge(book);
        assertEquals(BookState.VYPUJCENA, book.getState());

        assertEquals(loan, service.find(loan.getId()));
        assertEquals(loan, em.find(BookLoan.class, loan.getId()));
    }

    @Test
    void persistDoesNotPersistsBookLoanWithIncorrectDates() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        BookLoan loan = new BookLoan(DateUtils.getDateFromLocalDate(LocalDate.now()), DateUtils.getDateFromLocalDate(LocalDate.now()), user, book);
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));

        loan.setDateFrom(DateUtils.getDateFromLocalDate(LocalDate.now()));
        loan.setDateTo(DateUtils.getDateFromLocalDate(LocalDate.now().minusYears(1)));
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));

        loan.setDateTo(DateUtils.getDateFromLocalDate(LocalDate.now().minusMonths(1)));
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));

        loan.setDateTo(DateUtils.getDateFromLocalDate(LocalDate.now().minusDays(1)));
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));

        loan.setDateTo(DateUtils.getDateFromLocalDate(LocalDate.now().plusDays(6)));
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));

        loan.setDateTo(DateUtils.getDateFromLocalDate(LocalDate.now().minusDays(10)));
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));

        loan.setDateTo(DateUtils.getDateFromLocalDate(LocalDate.now().minusDays(34)));
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));

        loan.setDateTo(DateUtils.getDateFromLocalDate(LocalDate.now().minusDays(583)));
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));

        loan.setDateTo(DateUtils.getDateFromLocalDate(LocalDate.now().plusDays(4)));
        assertThrows(BookLoanDatesException.class, () -> service.persist(loan));
    }

    @Test
    void updateUpdatesBookLoan() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        BookLoan loan = new BookLoan(DateUtils.getDateFromLocalDate(LocalDate.now()), DateUtils.getDateFromLocalDate(LocalDate.now().plusDays(12)), user, book);
        em.persist(loan);

        assertEquals(loan, service.find(loan.getId()));

        loan.setReturned(true);
        service.update(loan);

        assertEquals(loan, em.find(BookLoan.class, loan.getId()));
    }

    @Test
    void getCurrentBookLoanReturnsCurrentBookLoan() {
        User user = new User();
        Book book = new Book();
        em.persist(book);
        em.persist(user);

        BookLoan loan = new BookLoan(DateUtils.getDateFromLocalDate(LocalDate.now()), DateUtils.getDateFromLocalDate(LocalDate.now().plusDays(12)), user, book);
        service.persist(loan);

        assertEquals(loan, service.find(loan.getId()));

        assertEquals(loan, service.getCurrentBookLoan(book));

    }

    @Test
    void returnBookSetsLoanIsReturnedToTrue() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        BookLoan loan = new BookLoan(DateUtils.getDateFromLocalDate(LocalDate.now()), DateUtils.getDateFromLocalDate(LocalDate.now().plusDays(7)), user, book);
        assertDoesNotThrow(() -> service.persist(loan));

        em.merge(book);
        assertEquals(BookState.VYPUJCENA, book.getState());

        assertEquals(loan, service.find(loan.getId()));
        service.returnLoanedBook(book);
        em.merge(loan);
        assertTrue(loan.isReturned());

    }

    @Test
    void returnBookSetsBookStatusToReservedWithActiveReservation() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        BookLoan loan = new BookLoan(DateUtils.getDateFromLocalDate(LocalDate.now()), DateUtils.getDateFromLocalDate(LocalDate.now().plusDays(7)), user, book);
        assertDoesNotThrow(() -> service.persist(loan));

        em.merge(book);
        assertEquals(BookState.VYPUJCENA, book.getState());

        Reservation reservation = new Reservation(user, book);
        reservationService.persist(reservation);

        assertEquals(loan, service.find(loan.getId()));
        service.returnLoanedBook(book);
        em.merge(loan);
        assertTrue(loan.isReturned());

        em.merge(book);
        assertEquals(BookState.REZERVOVANA, book.getState());
    }

    @Test
    void returnBookSetsBookStatusToFreeWithNoActiveReservation() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        assertEquals(BookState.VOLNA, book.getState());

        BookLoan loan = new BookLoan(DateUtils.getDateFromLocalDate(LocalDate.now()), DateUtils.getDateFromLocalDate(LocalDate.now().plusDays(7)), user, book);
        assertDoesNotThrow(() -> service.persist(loan));

        em.merge(book);
        assertEquals(BookState.VYPUJCENA, book.getState());

        assertEquals(loan, service.find(loan.getId()));
        service.returnLoanedBook(book);
        em.merge(loan);
        assertTrue(loan.isReturned());

        em.merge(book);
        assertEquals(BookState.VOLNA, book.getState());
    }

    @Test
    void makeStandardLengthBookLoanFromReservationCreatesBookLoan() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        assertEquals(BookState.VOLNA, book.getState());

        Reservation reservation = new Reservation(user, book);
        reservationService.persist(reservation);

        assertEquals(BookState.REZERVOVANA, book.getState());
        assertEquals(ReservationState.AKTIVNI, reservation.getState());

        BookLoan loan = service.makeStandardLengthBookLoanFromReservation(reservation);

        assertEquals(reservation.getUser(), loan.getUser());
        assertEquals(reservation.getBook(), loan.getBook());

        em.merge(book);
        assertEquals(BookState.VYPUJCENA, book.getState());
        assertEquals(ReservationState.VYDANA, reservation.getState());


    }
}