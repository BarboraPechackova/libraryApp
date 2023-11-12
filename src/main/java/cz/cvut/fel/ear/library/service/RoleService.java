package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.RoleDao;
import cz.cvut.fel.ear.library.dao.UserDao;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    private final RoleDao roleDao;

    private final UserDao userDao;

    @Autowired
    public RoleService(RoleDao dao, UserDao userDao) {
        this.roleDao = dao;
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public Role find(Integer id) {
        return roleDao.find(id);
    }

    @Transactional
    public void persist(Role role) {
        roleDao.persist(role);
    }

    @Transactional
    public void update(Role role) {
        roleDao.update(role);
    }


    /**
     * Removes all users with the role. Restricts admin role removal.
     *
     * @return {@code true} if the role was removed, {@code false} otherwise
     */
    @Transactional
    public void removeRole(String role) {
        Objects.requireNonNull(role);

        if (role.equals("ADMIN")) {
            throw new IllegalArgumentException("Cannot remove admin role");
        }

        // Finds all users with the role and removes the role from them
        List<User> users = roleDao.findAllUsersByRoleName(role);
        for (User user : users) {
            removeRoleFromUser(user, role);
        }
    }

    /**
     * Adds role to user.
     */
    @Transactional
    public void addRoleToUser(User user, String role) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(role);

        // gets all roles of the user
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }
        List<Role> userRoles = user.getRoles();
        // creates new role
        Role newRole = new Role();
        newRole.setRole(role);
        // adds new role to the list of roles
        userRoles.add(newRole);
        // sets the list of roles to the user
        user.setRoles(userRoles);
        // updates the user
        userDao.update(user);
        // persists the new role
        roleDao.persist(newRole);
    }

    /**
     * Removes role from user. Restricts lat/only admin role removal.
     *
     * @return {@code true} if the role was removed, {@code false} otherwise
     */
    @Transactional
    public boolean removeRoleFromUser(User user, String role) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(role);

        // gets all roles of the user
        List<Role> userRoles = user.getRoles();
        // finds the user role to remove
        Role roleToRemove = null;
        for (Role r : userRoles) {
            if (r.getRole().equals(role)) {
                roleToRemove = r;
                break;
            }
        }
        // if the user does not have the role, returns false
        if (roleToRemove == null) {
            return false;
        }
        // checks if the role is admin
        if (roleToRemove.getRole().equals("ADMIN")) {
            // if the user is the only admin, returns false
            if (roleDao.findAllAdmins() == null || roleDao.findAllAdmins().size() == 1) {
                return false;
            }
        }
        // removes the role from the Userlist of roles
        userRoles.remove(roleToRemove);
        // sets the list of roles to the user
        user.setRoles(userRoles);
        // updates the user
        userDao.update(user);

        // removes the role from the roles
        roleDao.remove(roleToRemove);
        return true;
    }
}
