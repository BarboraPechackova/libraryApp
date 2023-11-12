package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class RoleServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RoleService roleService;


    @Test
    public void removeUserRoleRemovesRoleFromAllUsers() {
        final User usr1 = generateUser("john_doe");
        roleService.addRoleToUser(usr1, "USER");
        final User usr2 = generateUser("jane_doe");
        roleService.addRoleToUser(usr2, "USER");
        roleService.addRoleToUser(usr2, "ADMIN");

        roleService.removeRole("USER");

        assertEquals(0, usr1.getRoles().size());
        assertEquals(1, usr2.getRoles().size());
        assertEquals("ADMIN", usr2.getRoles().get(0).getRole());
    }

    @Test
    public void removeAdminRoleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> roleService.removeRole("ADMIN"));
    }

    @Test
    public void addRoleToUserAddsRoleToUser() {
        final User usr1 = generateUser("john_doe");

        roleService.addRoleToUser(usr1, "USER");

        assertEquals(1, usr1.getRoles().size());
        assertEquals("USER", usr1.getRoles().get(0).getRole());
        // check if role is in the table Role in the database
        assertEquals(1, roleService.findAllUsers().size());
    }

    @Test
    public void addMultipleRolesToUserAddsRolesToUser() {
        final User usr1 = generateUser("john_doe");

        roleService.addRoleToUser(usr1, "USER");
        roleService.addRoleToUser(usr1, "ADMIN");

        assertEquals(2, usr1.getRoles().size());
        assertEquals("USER", usr1.getRoles().get(0).getRole());
        assertEquals("ADMIN", usr1.getRoles().get(1).getRole());
        // check if roles are in the table Role in the database
        assertEquals(1, roleService.findAllUsers().size());
        assertEquals(1, roleService.findAllAdmins().size());
    }

    @Test
    public void removeRoleFromUserRemovesRoleFromUser() {
        final User usr1 = generateUser("john_doe");

        roleService.addRoleToUser(usr1, "USER");
        roleService.addRoleToUser(usr1, "ADMIN");
        roleService.removeRoleFromUser(usr1, "USER");

        assertEquals(1, usr1.getRoles().size());
        assertEquals("ADMIN", usr1.getRoles().get(0).getRole());
        // check if only one role is in the table Role in the database
        assertEquals(1, roleService.findAllAdmins().size());
        assertEquals(0, roleService.findAllUsers().size());
    }

    @Test
    public void removeAdminRoleFromOnlyAdminUserDoesntRemoveRoleFromUser() {
        final User usr1 = generateUser("john_doe");

        roleService.addRoleToUser(usr1, "USER");
        roleService.addRoleToUser(usr1, "ADMIN");
        boolean removed = roleService.removeRoleFromUser(usr1, "ADMIN");

        assertFalse(removed);
        assertEquals(2, usr1.getRoles().size());
    }

    private User generateUser(String username) {
        final User user = new User();
        user.setUsername(username);
        em.persist(user);
        return user;
    }



}