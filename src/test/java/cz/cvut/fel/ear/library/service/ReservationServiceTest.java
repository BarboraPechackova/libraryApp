package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.enums.BookState;
import cz.cvut.fel.ear.library.model.enums.ReservationState;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ReservationServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ReservationService service;


    @Test
    void persist() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        assertEquals(BookState.VOLNA, book.getState());

        Reservation reservation = new Reservation(user, book);
        service.persist(reservation);

        assertEquals(BookState.REZERVOVANA, book.getState());
        assertEquals(ReservationState.AKTIVNI, reservation.getState());

        assertEquals(reservation, service.find(reservation.getId()));
        assertEquals(reservation, em.find(Reservation.class, reservation.getId()));
    }

    @Test
    void bookHasActiveReservations() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        assertEquals(BookState.VOLNA, book.getState());
        assertFalse(service.bookHasActiveReservations(book));

        Reservation reservation = new Reservation(user, book);
        service.persist(reservation);

        assertEquals(BookState.REZERVOVANA, book.getState());
        assertEquals(ReservationState.AKTIVNI, reservation.getState());
        assertTrue(service.bookHasActiveReservations(book));

        assertEquals(reservation, service.find(reservation.getId()));
        assertEquals(reservation, em.find(Reservation.class, reservation.getId()));
    }

    @Test
    void getAllUserReservation() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        ArrayList<Reservation> allReservations = new ArrayList<Reservation>(10);
        for (int i = 0; i < 10; i++) {
            Reservation reservation = new Reservation(user, book);
            service.persist(reservation);
            allReservations.add(reservation);
        }

        int i = 0;
        for (Reservation reservation : service.getAllUserReservation(user)) {
            i++;
            if (i%2 == 0) continue;
            service.setReservationStatusToCanceled(reservation);
            service.update(reservation);
        }

        assertEquals(allReservations, service.getAllUserReservation(user));

    }

    @Test
    void getAllActiveUserReservation() {
        Book book = new Book();
        User user = new User();
        em.persist(book);
        em.persist(user);

        ArrayList<Reservation> activeReservations = new ArrayList<Reservation>(10);
        for (int i = 0; i < 10; i++) {
            Reservation reservation = new Reservation(user, book);
            service.persist(reservation);
            service.setReservationStatusToCanceled(reservation);

        }

        int i = 0;
        for (Reservation reservation : service.getAllUserReservation(user)) {
            i++;
            if (i%2 == 0) continue;
            service.setReservationStatusToActive(reservation);
            service.update(reservation);
            activeReservations.add(reservation);
        }

        assertEquals(activeReservations, service.getAllActiveUserReservation(user));

    }
}