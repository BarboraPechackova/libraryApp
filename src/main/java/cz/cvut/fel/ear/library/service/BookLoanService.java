package cz.cvut.fel.ear.library.service;


import cz.cvut.fel.ear.library.dao.BookDao;
import cz.cvut.fel.ear.library.dao.BookLoanDao;
import cz.cvut.fel.ear.library.exceptions.*;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.enums.BookState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static cz.cvut.fel.ear.library.util.Constants.MIN_DAYS_OF_LOAN;
import static cz.cvut.fel.ear.library.util.Constants.STANDARD_LOAN_LENGTH_IN_MONTHS;
import static cz.cvut.fel.ear.library.util.DateUtils.getDateFromLocalDate;

@Service
public class BookLoanService {
    private final BookLoanDao dao;
    private final BookDao bookDao;
    private final ReservationService reservationService;

    @Autowired
    public BookLoanService(BookLoanDao dao, BookDao bookDao, ReservationService reservationService) {
        this.dao = dao;
        this.bookDao = bookDao;
        this.reservationService = reservationService;
    }

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
    public void persist(BookLoan loan)  {
        Objects.requireNonNull(loan);

        Book temp = loan.getBook();
        temp.setState(BookState.VYPUJCENA);
        bookDao.update(temp);

        dao.persist(loan);
    }

    @Transactional
    public void update(BookLoan loan) {
        Objects.requireNonNull(loan);
        validateBookLoan(loan);
        BookLoan currentBookLoan = getCurrentBookLoan(loan.getBook());
        if (currentBookLoan == null)
            throw new BookNotLoanedException("The book you are trying to return is not loaned! Therefore it cannot be returned.");
        dao.update(loan);
    }

    @Transactional
    public void delete(BookLoan loan) {
        if (!loan.isReturned())
            throw new BookNotReturnedException("Cannot delete a book has not been returned and is still loaned!");
        dao.remove(loan);
    }

    @Transactional(readOnly = true)
    public BookLoan getCurrentBookLoan(Book book) {
        BookLoan loan = dao.getCurrentLoanOfBook(book);
        if (loan == null)
            throw new BookNotLoanedException("Book is not currently loaned!");

        return loan;
    }

    @Transactional
    public void returnLoanedBook(Book book) {
        Objects.requireNonNull(book);
        BookLoan loan = dao.getCurrentLoanOfBook(book);
        if (loan == null)
            throw new BookNotLoanedException("The book you are trying to return is not loaned! Therefore it cannot be returned.");
        loan.setReturned(true);
        if (reservationService.bookHasActiveReservations(book))
            book.setState(BookState.REZERVOVANA);
        else
            book.setState(BookState.VOLNA);
        bookDao.update(book);
        dao.update(loan);
    }

    @Transactional
    public void returnBookLoan(BookLoan bookLoan) {
        Objects.requireNonNull(bookLoan);
        if (bookLoan.isReturned()) {
            throw new BookAlreadyReturnedException("The book loan you are trying to return was already returned! Therefore it cannot be returned again.");
        }

        bookLoan.setReturned(true);
        Book book = bookLoan.getBook();
        if (reservationService.bookHasActiveReservations(book)) {
            book.setState(BookState.REZERVOVANA);
        } else {
            book.setState(BookState.VOLNA);
        }
        bookDao.update(book);
        dao.update(bookLoan);
    }

    /**
     * Makes a new standard length BookLoan from a reservation.
     *
     * @see cz.cvut.fel.ear.library.util.Constants
     *
     * @param reservation - reservation
     * @return a new BookLoan
     */
    @Transactional
    public BookLoan makeStandardLengthBookLoanFromReservation(Reservation reservation) {
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

        BookLoan loan = new BookLoan(getDateFromLocalDate(date_from), getDateFromLocalDate(date_to), reservation.getUser(), reservation.getBook());
        persist(loan);

        reservationService.setReservationStatusToLoaned(reservation);
        return loan;
    }

    private void validateLoanDates(LocalDate dateFrom, LocalDate dateTo) throws BookLoanDatesException {
        LocalDate temp = dateFrom.plusDays(MIN_DAYS_OF_LOAN);
        if (temp.getYear() > dateTo.getYear())
            throw new BookLoanDatesException("The starting year of the BookLoan is greater than the ending year!");

        if (temp.getMonthValue() > dateTo.getMonthValue() && !(temp.getYear() < dateTo.getYear()))
            throw new BookLoanDatesException("The starting month of the BookLoan is greater than the ending month!");

        if (temp.getDayOfMonth() > dateTo.getDayOfMonth() && !(temp.getMonthValue() < dateTo.getMonthValue()) && !(temp.getYear() < dateTo.getYear()))
            throw new BookLoanDatesException("The starting month of the BookLoan is greater than the ending month!");
    }

    private void validateBookLoan(BookLoan loan) throws BookLoanDatesException {
        validateLoanDates(loan.getDateFrom().toLocalDate(), loan.getDateTo().toLocalDate());
    }

    public boolean bookHasActiveLoan(Book book) {
        return dao.getCurrentLoanOfBook(book) != null;
    }

    @Transactional(readOnly = true)
    public boolean hasUnreturnedLoansOfUserBook(User user, int bookId) {
        List<BookLoan> activeLoans = dao.findActiveLoansByUserAndBook(user, bookId);
        return !activeLoans.isEmpty();
    }

}
