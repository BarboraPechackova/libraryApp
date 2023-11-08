package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.BookDao;
import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookState;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final BookDao dao;


    @Autowired
    public BookService(BookDao dao) {
        this.dao = dao;
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
    public void persist(Book book) throws InvalidArgumentException {
        Objects.requireNonNull(book);
        validate(book);
        dao.persist(book);
    }

    @Transactional
    public void update(Book book) throws InvalidArgumentException {
        Objects.requireNonNull(book);
        validate(book);
        dao.update(book);
    }

    @Transactional
    public List<Book> findAllFromUser(User user) {
        Objects.requireNonNull(user);
        return dao.findAll(user);
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

    private void validate(Book book) throws InvalidArgumentException {
        if (book.getPrice() < 0) {
            throw new InvalidArgumentException("Price must not be negative");
        }
    }
}
