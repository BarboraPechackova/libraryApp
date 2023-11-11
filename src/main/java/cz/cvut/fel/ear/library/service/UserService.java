package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.RoleDao;
import cz.cvut.fel.ear.library.dao.UserDao;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserDao dao;

    private final RoleDao roleDao;

    @Autowired
    public UserService(UserDao dao, RoleDao roleDao) {
        this.dao = dao;
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public User find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        dao.persist(user);
    }

    @Transactional
    public void addRoleToUser(User user, Role role) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(role);
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        dao.update(user);
    }

    /**
     * Removes role from user
     *
     * @return {@code true} if the role was removed, {@code false} otherwise
     */
    @Transactional
    public boolean removeRoleFromUser(User user, Role role) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(role);
        List<Role> roles = user.getRoles();
        boolean roleRemoved = roles.remove(role);
        if (roleRemoved) {
            user.setRoles(roles);
            dao.update(user);
        }
        return roleRemoved;
    }

    @Transactional
    public void removeUser(User user) {
        // TODO: Implement remove logic
        Objects.requireNonNull(user);
        dao.remove(user);
    }
}
