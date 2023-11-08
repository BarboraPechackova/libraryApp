package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao extends BaseDao<Book>{

    public BookDao() {
        super(Book.class);
    }


}
