package cz.cvut.fel.ear.library.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserService userService;

    @Test
    public void addRoleToUserAddsRoleToUser() {}

    @Test
    public void removeRoleFromUserRemovesRoleFromUser() {}

    @Test
    public void removeUserRemovesUser() {}

}