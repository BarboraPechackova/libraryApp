package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.DemoApplication;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ComponentScan(basePackageClasses = DemoApplication.class)
@ActiveProfiles("test")
public class RoleDaoTest {

    // TestEntityManager provides additional test-related methods (it is Spring-specific)
    @Autowired
    private TestEntityManager em;

    @Autowired
    private RoleDao roleDao;

    @Test
    public void findAllBasicUsersFindsAllUsers() {
        User usr1 = generateUser( "john_doe", "john", "doe", "john.doe@gmail.com", "+420604444444", "2100000000/2010");
        User usr2 = generateUser("jane_doe", "jane", "doe", "jane.doe@gmail.com", "+420604444445", "2200000000/2010");
        Role role1 = generateRoleforUser("USER", 0);
        Role role2 = generateRoleforUser("USER", 1);

        List<User> users = roleDao.findAllUsers();
        assert(users.size() == 2);

    }

    private Role generateRoleforUser(String name, int userid) {
        final Role role = new Role();
        role.setRole(name);
        role.setIdUser(userid);
        em.persist(role);
        return role;
    }

    private User generateUser(String username, String firstName, String surname, String email, String phone, String bankAccount) {
        final User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setBankAccount(bankAccount);

        em.persist(user);
        return user;
    }

}