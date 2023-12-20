package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.BookDao;
import cz.cvut.fel.ear.library.dao.ReservationDao;
import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.enums.BookState;
import cz.cvut.fel.ear.library.model.enums.ReservationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private final ReservationDao dao;
    private final BookDao bookDao;

    @Autowired
    public ReservationService(ReservationDao dao, BookDao bookDao) {
        this.dao = dao;
        this.bookDao = bookDao;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Reservation find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public boolean bookHasActiveReservations(Book book) {
        Objects.requireNonNull(book);
        return !dao.getActiveReservationsOfBook(book).isEmpty();
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsOfBook(Book book) {
        Objects.requireNonNull(book);
        return dao.getAllReservationsOfBook(book);
    }

    @Transactional
    public void persist(Reservation reservation) throws InvalidArgumentException {
        Objects.requireNonNull(reservation);
        Book book = reservation.getBook();
        Objects.requireNonNull(book);
        // When the book is free (VOLNA) then set it's state to reserved (REZERVOVANA)
        if (book.getState() == BookState.VOLNA) {
            book.setState(BookState.REZERVOVANA);
            bookDao.update(book);
        }

        dao.persist(reservation);
    }

    @Transactional
    public void update(Reservation reservation) throws InvalidArgumentException {
        Objects.requireNonNull(reservation);
        dao.update(reservation);
    }

    @Transactional
    public void delete(Reservation reservation) {
        dao.remove(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllUserReservation(User user) {
        Objects.requireNonNull(user);
        return dao.getAllUserReservations(user);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllActiveUserReservation(User user) {
        Objects.requireNonNull(user);
        return dao.getActiveUserReservations(user);
    }

    /**
     * This method should not be needed, because we want to keep a history of the reservations, but it is here just in case we want to wreak havoc.
     */
    @Transactional
    public void deleteAllUserReservations(User user) {
        Objects.requireNonNull(user);
        for (Reservation reservation : getAllUserReservation(user)) {
            delete(reservation);
        }
    }

    @Transactional
    public void deleteActiveUserReservations(User user) {
        Objects.requireNonNull(user);
        for (Reservation reservation : getAllUserReservation(user)) {
            delete(reservation);
        }
    }

    @Transactional
    public void deleteActiveBookReservations(Book book) {
        Objects.requireNonNull(book);
        for (Reservation reservation : dao.getActiveReservationsOfBook(book)) {
            delete(reservation);
        }
    }

    @Transactional(readOnly = true)
    public List<Reservation> getUserReservationsOfBook(User user, Book book) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(book);
        return dao.getUserReservationsOfBook(user, book);
    }

    @Transactional
    public void setReservationStatusToActive(Reservation reservation) {
        Objects.requireNonNull(reservation);
        reservation.setState(ReservationState.AKTIVNI);
        dao.update(reservation);
    }

    @Transactional
    public void setReservationStatusToCanceled(Reservation reservation) {
        Objects.requireNonNull(reservation);
        reservation.setState(ReservationState.ZRUSENA);
        dao.update(reservation);
    }

    @Transactional
    public void setReservationStatusToLoaned(Reservation reservation) {
        Objects.requireNonNull(reservation);
        reservation.setState(ReservationState.VYDANA);
        dao.update(reservation);
    }


}

