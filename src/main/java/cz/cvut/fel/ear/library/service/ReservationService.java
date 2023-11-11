package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.BookLoanDao;
import cz.cvut.fel.ear.library.dao.ReservationDao;
import cz.cvut.fel.ear.library.exceptions.BookIsAlreadyLoanedException;
import cz.cvut.fel.ear.library.exceptions.BookIsNotReturnedException;
import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private final ReservationDao dao;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public ReservationService(ReservationDao dao, UserService userService, BookService bookService) {
        this.dao = dao;
        this.userService = userService;
        this.bookService = bookService;
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

}

