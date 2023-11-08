package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookServiceTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BookService service;

    @Test
    public void firstRandomTest() throws InvalidArgumentException {
        final Book book = new Book();
        service.persist(book);

    }
}
