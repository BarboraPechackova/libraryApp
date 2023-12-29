package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.BookDao;
import cz.cvut.fel.ear.library.dao.RatingDao;
import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Rating;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class RatingService {
    private final RatingDao dao;

    @Autowired
    public RatingService(RatingDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<Rating> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Rating find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Rating rating) {
        Objects.requireNonNull(rating);
        validate(rating);
        dao.persist(rating);
    }

    @Transactional
    public void update(Rating rating) {
        Objects.requireNonNull(rating);
        validate(rating);
        dao.update(rating);
    }

    @Transactional
    @PostFilter("hasRole('ROLE_ADMIN') or (rating.user.username == principal.username)")
    public void remove(Rating rating) {
        Objects.requireNonNull(rating);
        dao.remove(rating);
    }

    @Transactional
    public List<Rating> findAllFromUser(User user) {
        Objects.requireNonNull(user);
        return dao.findAll(user);
    }

    @Transactional
    public List<Rating> findAllFromBook(Book book) {
        Objects.requireNonNull(book);
        return dao.findAll(book);
    }

    public void validate(Rating rating) throws InvalidArgumentException {
        if (rating.getPoints() < 0 || rating.getPoints() > 10) {
            throw new InvalidArgumentException("Rating must be between 0 and 10");
        }
    }
}
