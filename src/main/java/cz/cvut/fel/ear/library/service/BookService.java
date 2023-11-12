package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.BookDao;
import cz.cvut.fel.ear.library.dao.BookLoanDao;
import cz.cvut.fel.ear.library.exceptions.BookAlreadyLoanedException;
import cz.cvut.fel.ear.library.exceptions.BookNotReturnedException;
import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.enums.BookState;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final BookDao dao;
    private final BookLoanDao bookLoanDao;

    @Autowired
    public BookService(BookDao dao, BookLoanDao bookLoanDao) {
        this.dao = dao;
        this.bookLoanDao = bookLoanDao;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Book find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Book book) {
        Objects.requireNonNull(book);
        validate(book);
        dao.persist(book);
    }

    @Transactional
    public void persistAll(List<Book> books) {
        for (Book b : books) {
            persist(b);
        }
    }

    @Transactional
    public void update(Book book) {
        Objects.requireNonNull(book);
        validate(book);
        dao.update(book);
    }

    @Transactional
    public List<Book> findAllFromUser(User user) {
        Objects.requireNonNull(user);
        return dao.findAllFromUser(user);
    }

    @Transactional
    public List<Book> findAllVisibleFromUser(User user) {
        Objects.requireNonNull(user);
        return dao.findAllFromUser(user,true);
    }

    @Transactional
    public void disable(Book book) {
        Objects.requireNonNull(book);
        book.setVisible(false);
        dao.update(book);
    }

    @Transactional
    public void enable(Book book) {
        Objects.requireNonNull(book);
        book.setVisible(true);
        dao.update(book);
    }

    @Transactional
    public void setFree(Book book) {
        Objects.requireNonNull(book);
        book.setState(BookState.VOLNA);
        dao.update(book);
    }

    @Transactional
    public void setReserved(Book book) {
        Objects.requireNonNull(book);
        book.setState(BookState.REZERVOVANA);
        dao.update(book);
    }

    @Transactional
    public void setBooked(Book book) {
        Objects.requireNonNull(book);
        book.setState(BookState.VYPUJCENA);
        dao.update(book);
    }

    // TODO dalsi metody, upravit ty co uz mam jestli ukazovat visible only

    public List<Book> findByName(String name) {
        Objects.requireNonNull(name);
        return dao.findByName(name);
    }

    public List<Book> findVisibleByName(String name) {
        Objects.requireNonNull(name);
        return dao.findByName(name,true);
    }

    public void findBooksByState(BookState state) {}

    public List<Book> findAllVisible() {
        return dao.findAllVisible(true);
    }

    public void removeBooksFromUser (User user) {

    }

    private void validate(Book book) {
        if (book.getPrice() < 0) {
            throw new InvalidArgumentException("Price must not be negative");
        }
    }
}
