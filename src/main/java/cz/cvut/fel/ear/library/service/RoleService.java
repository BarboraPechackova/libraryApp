package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.RoleDao;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    private final RoleDao dao;

    @Autowired
    public RoleService(RoleDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Role find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Role role) {
        dao.persist(role);
    }

    @Transactional
    public void update(Role role) {
        dao.update(role);
    }


    /**
     * Removes role and all users with the role. Restricts admin role removal.
     *
     * @return {@code true} if the role was removed, {@code false} otherwise
     */
    @Transactional
    public void remove(Role role) {
        // TODO: Implement remove logic
        Objects.requireNonNull(role);

        if (role.getRole().equals("ADMIN")) {
            throw new IllegalArgumentException("Cannot remove admin role");
        }


        dao.update(role);
    }
}
