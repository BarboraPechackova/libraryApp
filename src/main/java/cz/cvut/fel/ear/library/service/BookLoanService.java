package cz.cvut.fel.ear.library.service;


import cz.cvut.fel.ear.library.dao.BookLoanDao;
import cz.cvut.fel.ear.library.exceptions.*;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BookLoanService {

    private final BookLoanDao dao;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public BookLoanService(BookLoanDao dao, UserService userService, BookService bookService) {
        this.dao = dao;
        this.userService = userService;
        this.bookService = bookService;
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
        dao.update(loan);
    }

    @Transactional
    public void delete(BookLoan loan) {
        if (!loan.isReturned())
            throw new BookIsNotReturnedException("Cannot delete a book has not been returned and is still loaned!");
        dao.remove(loan);
    }

    private void validateBookLoan(BookLoan loan) throws InvalidArgumentException {
        if (loan.getDateTo().before(loan.getDateFrom())) {
            throw new InvalidArgumentException("Date to must be larger than date from!");
        }
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
