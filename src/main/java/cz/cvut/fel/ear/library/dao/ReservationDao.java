package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ReservationDao extends BaseDao<Reservation> {
    public ReservationDao() {
        super(Reservation.class);
    }

    public List<Reservation> getAllReservationsOfBook(Book book) {
        TypedQuery<Reservation> q = em.createNamedQuery("Reservation.reservationsOfBook", Reservation.class);
        q.setParameter("book", book);
        return q.getResultList();
    }

    public List<Reservation> getUserReservationsOfBook(User user, Book book) {
        TypedQuery<Reservation> q = em.createNamedQuery("Reservation.userBookReservations", Reservation.class);
        q.setParameter("user", user);
        q.setParameter("book", book);
        return q.getResultList();
    }

    public List<Reservation> getAllUserReservations(User user) {
        TypedQuery<Reservation> q = em.createNamedQuery("Reservation.allUserReservations", Reservation.class);
        q.setParameter("user", user);
        return q.getResultList();
    }

    public List<Reservation> getActiveReservationsOfBook(Book book) {
        TypedQuery<Reservation> q = em.createNamedQuery("Reservation.activeBookReservations", Reservation.class);
        q.setParameter("book", book);
        return q.getResultList();
    }

    public List<Reservation> getActiveUserReservations(User user) {
        TypedQuery<Reservation> q = em.createNamedQuery("Reservation.activeUserReservations", Reservation.class);
        q.setParameter("user", user);
        return q.getResultList();
    }

    public List<Reservation> findActiveReservationsByUserAndBook(User user, int bookId) {
        TypedQuery<Reservation> query = em.createNamedQuery("Reservation.activeReservationsByUserAndBook", Reservation.class);
        query.setParameter("user", user);
        query.setParameter("bookId", bookId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
