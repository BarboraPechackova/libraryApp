package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class RoleDao extends BaseDao<Role>{
    public RoleDao() {
        super(Role.class);
    }

    public List<User> findAllAdmins() {
        return em.createNamedQuery("Role.findAllAdmins", User.class).getResultList();
    }

    public List<User> findAllUsers() {
        return em.createNamedQuery("Role.findAllBasicUsers", User.class).getResultList();
    }

    public List<User> findAllUsersByRoleName(String name) {
        Objects.requireNonNull(name);
        return em.createNamedQuery("Role.findAllUsersByRoleName", User.class).setParameter("name", name).getResultList();
    }
}
