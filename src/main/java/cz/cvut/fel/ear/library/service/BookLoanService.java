package cz.cvut.fel.ear.library.service;


import cz.cvut.fel.ear.library.dao.BookLoanDao;
import cz.cvut.fel.ear.library.exceptions.*;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static cz.cvut.fel.ear.library.util.Constants.STANDARD_LOAN_LENGTH_IN_MONTHS;
import static cz.cvut.fel.ear.library.util.DateUtils.getDateFromLocalDate;

@Service
public class BookLoanService {

    private final BookLoanDao dao;
    private final UserService userService;
    private final BookService bookService;
    private final ReservationService reservationService;

    @Autowired
    public BookLoanService(BookLoanDao dao, UserService userService, BookService bookService, ReservationService reservationService) {
        this.dao = dao;
        this.userService = userService;
        this.bookService = bookService;
        this.reservationService = reservationService;
    }


//    @Transactional(readOnly = true)
//    public Book getBook(BookLoan loan) {
//        return bookService.find(loan.getIdBook());
//    }
//
//    @Transactional(readOnly = true)
//    public User getUser(BookLoan loan) {
//        return userService.find(loan.getIdUser());
//    }

    @Transactional(readOnly = true)
    public List<BookLoan> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public BookLoan find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public List<BookLoan> getLoansOfBook(Book book) {
        return dao.getBookLoans(book);
    }

    @Transactional(readOnly = true)
    public List<BookLoan> getLoansOfUser(User user) {
        return dao.getUserLoans(user);
    }

    @Transactional
    public void persist(BookLoan loan) throws InvalidArgumentException {
        Objects.requireNonNull(loan);
        validateNewBookLoan(loan);
        dao.persist(loan);
    }

    @Transactional
    public void update(BookLoan loan) throws InvalidArgumentException {
        Objects.requireNonNull(loan);
        validateBookLoan(loan);
        bookService.setBooked(loan.getBook());
        dao.update(loan);
    }

    @Transactional
    public void delete(BookLoan loan) {
        if (!loan.isReturned())
            throw new BookIsNotReturnedException("Cannot delete a book has not been returned and is still loaned!");
        dao.remove(loan);
    }

    @Transactional(readOnly = true)
    public void getActualBookLoan(Book book) {
        dao.getCurrentLoanOfBook(book);
    }

    @Transactional()
    public void returnBook(Book book) {
        Objects.requireNonNull(book);
        BookLoan loan = dao.getCurrentLoanOfBook(book);
        loan.setReturned(true);
        dao.update(loan);
    }

    /**
     * Makes a new standard length BookLoan from a reservation.
     *
     * @see Constants
     *
     * @param reservation - reservation
     * @return a new BookLoan
     */
    @Transactional
    public BookLoan makeBookLoanFromReservation(Reservation reservation) {
        return makeBookLoanFromReservation(reservation, LocalDate.now(), LocalDate.now().plusMonths(STANDARD_LOAN_LENGTH_IN_MONTHS));
    }

    /**
     * Makes a new BookLoan from a reservation
     *
     * @param reservation - reservation
     * @param date_from - starting date of the loan
     * @param date_to - ending date of the loan
     * @return a new BookLoan
     */
    @Transactional
    public BookLoan makeBookLoanFromReservation(Reservation reservation, LocalDate date_from, LocalDate date_to) {
        Objects.requireNonNull(reservation);
        Objects.requireNonNull(date_from);
        Objects.requireNonNull(date_to);

        BookLoan loan = new BookLoan((Date) getDateFromLocalDate(date_from), (Date) getDateFromLocalDate(date_to), reservation.getUser(), reservation.getBook());
        validateNewBookLoan(loan);
        return loan;
    }

    private void validateLoanDates(LocalDate dateFrom, LocalDate dateTo) {
        LocalDate temp = dateFrom.plusDays(Constants.MIN_DAYS_OF_LOAN);
        if (temp.getYear() > dateTo.getYear())
            throw new BookLoanDatesException("The starting year of the BookLoan is greater than the ending year!");

        if (temp.getMonthValue() < dateTo.getMonthValue() && !(temp.getYear() < dateTo.getYear()))
            throw new BookLoanDatesException("The starting month of the BookLoan is greater than the ending month!");

        if (temp.getDayOfMonth() < dateTo.getDayOfMonth() && !(temp.getMonthValue() < dateTo.getMonthValue()))
            throw new BookLoanDatesException("The starting month of the BookLoan is greater than the ending month!");
    }

    private void validateBookLoan(BookLoan loan) throws InvalidArgumentException {
        validateLoanDates(loan.getDateFrom().toLocalDate(), loan.getDateTo().toLocalDate());
//        if (loan.getDateTo().before(loan.getDateFrom())) {
//            throw new InvalidArgumentException("Date to must be larger than date from!");
//        }
    }

    private void validateNewBookLoan(BookLoan loan) throws InvalidArgumentException {
        validateBookLoan(loan);
        if (isBookLoaned(loan.getBook())) {
            throw new BookIsAlreadyLoanedException("The book is already loaned!");
        }
//        if (loan.getDateTo().before(loan.getDateFrom())) {
//            throw new InvalidArgumentException("Date to must be larger than date from!");
//        }
    }

    private boolean isBookLoaned(Book book) {
        Objects.requireNonNull(book);
        return dao.isBookLoaned(book);
    }



}
