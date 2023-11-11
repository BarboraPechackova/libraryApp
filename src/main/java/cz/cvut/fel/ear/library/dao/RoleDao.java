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
}
