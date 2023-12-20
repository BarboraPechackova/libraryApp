package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.RoleDao;
import cz.cvut.fel.ear.library.dao.UserDao;
import cz.cvut.fel.ear.library.exceptions.AdminRemovalException;
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


    /**
     * Removes all users with the role. Restricts admin role removal.
     *
     * @return {@code true} if the role was removed, {@code false} otherwise
     */
    @Transactional
    public void removeRole(String role) {
        Objects.requireNonNull(role);

        if (role.equals("ADMIN")) {
            throw new AdminRemovalException("Cannot remove all admin roles");
        }

        // Finds all users with the role and removes the role from them
        List<User> users = roleDao.findAllUsersByRoleName(role);
        for (User user : users) {
            removeRoleFromUser(user, role);
        }
    }

    /**
     * Adds role to user
     * @param user
     * @param roleName
     */
    @Transactional
    public void addRoleToUser(User user, String roleName) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(roleName);

        // Initialize roles if null
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }

        // creates new role
        Role newRole = new Role();
        newRole.setRole(roleName);
        newRole.setUser(user);

        // adds the new role to the list of roles
        List<Role> userRoles = user.getRoles();
        userRoles.add(newRole);
        user.setRoles(userRoles);

        // persists the new role
        roleDao.persist(newRole);
        // updates the user
        userDao.update(user);
    }

    /**
     * Removes role from user. Restricts last/only admin role removal (throws exception).
     * @param user
     * @param roleName
     * @return {@code true} if the role was removed, {@code false} otherwise
     */
    @Transactional
    public boolean removeRoleFromUser(User user, String roleName) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(roleName);

        // gets all roles of the user
        List<Role> userRoles = user.getRoles();
        // finds the user role to remove
        Role roleToRemove = null;
        for (Role r : userRoles) {
            if (r.getRole().equals(roleName)) {
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
            // if the user is the only admin, raises exception
            if (roleDao.findAllAdmins() == null || roleDao.findAllAdmins().size() == 1) {
                throw new AdminRemovalException("Cannot remove the only admin");
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

    public List<User> findAllAdmins() {
        return roleDao.findAllAdmins();
    }

    public List<User> findAllUsers() {
        return roleDao.findAllUsers();
    }

    public List<User> findAllUsersByRoleName(String name) {
        Objects.requireNonNull(name);
        return roleDao.findAllUsersByRoleName(name);
    }

    public List<Role> findAllRolesOfUser(User user) {
        Objects.requireNonNull(user);
        return roleDao.findAllRolesOfUser(user);
    }
}
