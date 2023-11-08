package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Rating;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingDao extends BaseDao<Rating> {
    public RatingDao() {
        super(Rating.class);
    }

    public List<Rating> findAll(User user) {
        return em.createNamedQuery("Rating.findByUser",Rating.class).setParameter("idUser",user.getId()).getResultList();
    }

    public List<Rating> findAll(Book book) {
        return em.createNamedQuery("Rating.findByBook",Rating.class).setParameter("idBook",book.getId()).getResultList();
    }
}
