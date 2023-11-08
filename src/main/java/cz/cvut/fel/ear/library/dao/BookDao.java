package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.User;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao extends BaseDao<Book>{

    public BookDao() {
        super(Book.class);
    }

    public List<Book> findAll(User user) {
        return em.createNamedQuery("Book.findByUser",Book.class).setParameter("idUser",user.getId()).getResultList();
    }
}
