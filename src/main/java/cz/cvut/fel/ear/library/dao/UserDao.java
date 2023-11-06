package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User>{
    public UserDao() {
        super(User.class);
    }
}
