package cz.cvut.fel.ear.library.service;

import cz.cvut.fel.ear.library.exceptions.InvalidArgumentException;
import cz.cvut.fel.ear.library.model.Book;
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

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookServiceTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BookService service;

    @Test
    public void persistPersistsBook() throws InvalidArgumentException {
        final Book book = new Book();
        em.persist(book);
        service.persist(book);

        assertEquals(1,service.findAll().size());
    }

    @Test
    public void findAllWithUserReturnsOnlyBooksFromUser() {
        final User user = new User();
        final Book b1 = new Book();
        final Book b2 = new Book();
        b1.setUser(user);

        em.persist(user);
        em.persist(b1);
        em.persist(b2);
        service.persistAll(List.of(b1, b2));

        assertEquals(2,service.findAll().size());
        assertEquals(1,service.findAllFromUser(user).size());
    }

//    @Test
//    public void addBook() {
//        final Product p = Generator.generateProduct();
//        final Category category = new Category();
//        category.setName("test");
//        em.persist(p);
//        sut.persist(category);
//        sut.addProduct(category, p);
//
//        final Product result = em.find(Product.class, p.getId());
//        assertTrue(result.getCategories().stream().anyMatch(c -> c.getId().equals(category.getId())));
//    }
}
