package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.ReservationDao;
import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.enums.ReservationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private final ReservationDao dao;

    @Autowired
    public ReservationService(ReservationDao dao) {
        this.dao = dao;
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
        return dao.getActiveReservationsOfBook(book).size() != 0;
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsOfBook(Book book) {
        Objects.requireNonNull(book);
        return dao.getAllReservationsOfBook(book);
    }

    @Transactional
    public void persist(Reservation reservation) throws InvalidArgumentException {
        Objects.requireNonNull(reservation);
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

    @Transactional
    public void deleteUserReservations(User user) {
        Objects.requireNonNull(user);
        for (Reservation reservation : getAllUserReservation(user)) {
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

