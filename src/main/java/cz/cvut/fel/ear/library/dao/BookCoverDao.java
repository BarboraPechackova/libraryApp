package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.BookCover;
import org.springframework.stereotype.Repository;

@Repository
public class BookCoverDao extends PictureDao<BookCover> {
    public BookCoverDao() {
        super(BookCover.class);
    }
}
