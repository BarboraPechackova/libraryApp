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
    public void removeRoleRemovesRole() {

    }

    @Test
    public void addRoleToUserAddsRoleToUser() {
        final User usr1 = generateUser("john_doe");
        roleService.addRoleToUser(usr1, "USER");
        assertEquals(1, usr1.getRoles().size());
        assertEquals("USER", usr1.getRoles().get(0).getRole());
        // TODO: check if role is in the table Role in the database
    }

    @Test
    public void removeRoleFromUserRemovesRoleFromUser() {

    }

    private User generateUser(String username) {
        final User user = new User();
        user.setUsername(username);
        em.persist(user);
        return user;
    }



}