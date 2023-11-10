package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao extends BaseDao<Role>{
    public RoleDao() {
        super(Role.class);
    }

    // TODO: implement find all users by role method (using named query)
}
