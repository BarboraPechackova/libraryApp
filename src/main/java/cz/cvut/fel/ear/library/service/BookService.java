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
        validateUpdatePersist(book);
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
        validateUpdatePersist(book);
        dao.update(book);
    }

    @Transactional
    public void remove(Book book) {
        validateRemove(book);
        dao.remove(book);
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

    public List<Book> findByName(String name) {
        Objects.requireNonNull(name);
        return dao.findByName(name);
    }

    public List<Book> findByAuthor(String author) {
        Objects.requireNonNull(author);
        return dao.findByAuthor(author);
    }

    public List<Book> findByNameOrAuthor(String searchedWord) {
        Objects.requireNonNull(searchedWord);
        return dao.findByNameOrAuthor(searchedWord);
    }

    public List<Book> findVisibleByName(String name) {
        Objects.requireNonNull(name);
        return dao.findByName(name,true);
    }

    public List<Book> findAllVisible() {
        return dao.findAllVisible(true);
    }

    private void validateUpdatePersist(Book book) {
        if (book.getPrice() < 0) {
            throw new InvalidArgumentException("Price must not be negative");
        }
    }

    private void validateRemove(Book book) {
        if (book.getState() == BookState.VYPUJCENA) {
            throw new BookAlreadyLoanedException("Loaned book cannot be returned");
        }
    }
}
