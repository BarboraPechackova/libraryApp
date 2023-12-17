package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.BookCoverDao;
import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookCover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BookCoverService {
    private final BookCoverDao dao;

    @Autowired
    public BookCoverService(BookCoverDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<BookCover> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public BookCover find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(BookCover bookCover) throws InvalidArgumentException {
        Objects.requireNonNull(bookCover);
        dao.persist(bookCover);
    }

    @Transactional
    public void delete(BookCover bookCover) throws InvalidArgumentException {
        Objects.requireNonNull(bookCover);
        dao.remove(bookCover);
    }

    @Transactional
    public void update(BookCover bookCover) throws InvalidArgumentException {
        Objects.requireNonNull(bookCover);
        dao.update(bookCover);
    }

    @Transactional
    public List<BookCover> findAllFromBook(Book book) {
        Objects.requireNonNull(book);
        return dao.findAll(book);
    }
}
