package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UserDao extends BaseDao<User>{
    public UserDao() {
        super(User.class);
    }

    public List<User> findAll(Role role) {
        Objects.requireNonNull(role);
        return em.createNamedQuery("User.findByRole", User.class).setParameter("role", role.getRole())
                .getResultList();
    }

}
