package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookCover;
import cz.cvut.fel.ear.library.model.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookCoverDao extends PictureDao<BookCover> {
    public BookCoverDao() {
        super(BookCover.class);
    }

    public List<BookCover> findAll(Book book) {
        return em.createNamedQuery("Cover.findByBook",BookCover.class).setParameter("idBook",book.getId()).getResultList();
    }
}
