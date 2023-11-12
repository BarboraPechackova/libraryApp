package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Rating;
import cz.cvut.fel.ear.library.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class RatingServiceTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RatingService service;

    @Test
    public void persistWithNegativePointsThrowsInvalidArgument() {
        final Rating rating = new Rating();
        em.persist(rating);
        short points = -1;
        rating.setPoints(points);

        assertThrows(InvalidArgumentException.class, () -> service.persist(rating));
    }

    @Test
    public void findAllFromUserReturnsOnlyRatingsWrittenByGivenUser() {
        final User user = new User();
        final Rating r1 = new Rating();
        final Rating r2 = new Rating();
        r1.setUser(user);

        em.persist(user);
        em.persist(r1);
        em.persist(r2);
        service.persist(r1);
        service.persist(r2);

        assertEquals(2,service.findAll().size());
        assertEquals(1,service.findAllFromUser(user).size());
    }
}
