package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.DemoApplication;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackageClasses = DemoApplication.class)
@ActiveProfiles("test")
public class UserDaoTest {

    // TestEntityManager provides additional test-related methods (it is Spring-specific)
    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserDao userDao;

//    @Test
//    public void findAllUsersByRoleReturnsAllUsersByRole() {
//        final User usr1 = generateUser("john_doe", "john", "doe", "john.doe@gmail.com", "+420604444444", "2100000000/2010");
//        final User usr2 = generateUser("jane_doe", "jane", "doe", "jane.doe@gmail.com", "+420604444445", "2200000000/2010");
//        List<User> users = List.of(usr1, usr2);
//        final Role role1 = generateRoleforUser("USER", usr1.getId());
//        final Role role2 = generateRoleforUser("USER", usr2.getId());
//
//        final List<User> result = userDao.findAll(role1);
//        assertEquals(users.size(), result.size());
//        assertEquals(0, result.get(0).getId());
//        assertEquals(1, result.get(1).getId());
//    }

    private Role generateRoleforUser(String name, int userId) {
        final Role role = new Role();
        role.setRole(name);
        role.setIdUser(userId);
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