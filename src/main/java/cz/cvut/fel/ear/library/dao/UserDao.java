package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.BookCover;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UserDao extends BaseDao<User>{
    public UserDao() {
        super(User.class);
    }

    public User findByUsername(String username) {
        try {
            return em.createNamedQuery("User.getUserByUsername", User.class).setParameter("username",username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
