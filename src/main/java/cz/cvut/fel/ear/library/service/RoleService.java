package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.dao.RoleDao;
import cz.cvut.fel.ear.library.model.Role;
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

//    @Transactional(readOnly = true)
//    public List<Role> findAll(User user) {
//        return dao.findAll(user);
//    }

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

    @Transactional
    public void remove(Role role) {
        // TODO: Implement remove logic
        Objects.requireNonNull(role);

        dao.update(role);
    }

    // TODO: implemnt test for last method
}
