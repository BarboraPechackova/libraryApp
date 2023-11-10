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

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackageClasses = DemoApplication.class)
//@ActiveProfiles("test")
public class UserDaoTest {

    // TestEntityManager provides additional test-related methods (it is Spring-specific)
    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserDao userDao;

    @Test
    public void findAllUsersByRoleReturnsAllUsersByRole() {
        final Role role = generateRole("user");
        final User usr1 = generateUserwithRole(1, "john_doe", "john", "doe", "john.doe@gmail.com", "+420604444444", "2100000000/2010", role);
        final User usr2 = generateUserwithRole(2, "jane_doe", "jane", "doe", "jane.doe@gmail.com", "+420604444445", "2200000000/2010", role);
        List<User> users = List.of(usr1, usr2);

        final List<User> result = userDao.findAll(role);
        assertEquals(users.size(), result.size());
        assertEquals(usr1.getId(), result.get(0).getId());
        assertEquals(usr2.getId(), result.get(1).getId());
    }

    private Role generateRole(String name) {
        final Role role = new Role();
        role.setRole(name);
        em.persist(role);
        return role;
    }

    private User generateUserwithRole(int id, String username, String firstName, String surname, String email, String phone, String bankAccount, Role role) {
        final User user = new User();
        user.setId(id);
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