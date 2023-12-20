package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.LibraryApplication;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ComponentScan(basePackageClasses = LibraryApplication.class)
@ActiveProfiles("test")
public class RoleDaoTest {

    // TestEntityManager provides additional test-related methods (it is Spring-specific)
    @Autowired
    private TestEntityManager em;

    @Autowired
    private RoleDao roleDao;

    @Test
    public void findAllBasicUsersFindsAllUsers() {
        generateUserWithRole("john_doe", "USER");
        generateUserWithRole("jane_doe", "USER");

        List<User> users = roleDao.findAllUsers();
        assertEquals(2, users.size());
        assertEquals("john_doe", users.get(0).getUsername());
        assertEquals("jane_doe", users.get(1).getUsername());
    }

    @Test
    public void findAllAdminsFindsAllAdmins() {
        generateUserWithRole("john_doe", "ADMIN");
        generateUserWithRole("jane_doe", "ADMIN");

        List<User> users = roleDao.findAllAdmins();
        assertEquals(2, users.size());
        assertEquals("john_doe", users.get(0).getUsername());
        assertEquals("jane_doe", users.get(1).getUsername());
    }

    @Test
    public void findAllUsersByRoleNameFindsAllUsersWithRole() {
        generateUserWithRole("john_doe", "USER");
        generateUserWithRole("jane_doe", "ADMIN");

        List<User> userList = roleDao.findAllUsersByRoleName("USER");
        assertEquals(1, userList.size());
        assertEquals("john_doe", userList.get(0).getUsername());

        userList = roleDao.findAllUsersByRoleName("ADMIN");
        assertEquals(1, userList.size());
        assertEquals("jane_doe", userList.get(0).getUsername());
    }

    private void generateUserWithRole(String username, String roleName) {
        final User user = new User();
        user.setUsername(username);

        Role role = new Role();
        role.setRole(roleName);
        role.setUser(user);

        user.setRoles(List.of(role));

        em.persist(role);
        em.persist(user);
    }

}