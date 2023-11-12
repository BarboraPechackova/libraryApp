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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookServiceTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BookService service;

    @Test
    public void persistPersistsBook() {
        final Book book = new Book();
        em.persist(book);
        service.persist(book);

        assertEquals(1,service.findAll().size());
    }

    @Test
    public void persistWithNegativePriceThrowsInvalidArgument() throws InvalidArgumentException {
        final Book book = new Book();
        book.setPrice(-1);
        em.persist(book);
        assertThrows(InvalidArgumentException.class, () -> service.persist(book));
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

    @Test
    public void findAllVisibleFromUserReturnsOnlyVisible() {
        final User user = new User();
        final Book b1 = new Book();
        final Book b2 = new Book();
        b1.setUser(user);
        b1.setVisible(true);

        b2.setUser(user);
        b2.setVisible(false);

        em.persist(user);
        em.persist(b1);
        em.persist(b2);
        service.persistAll(List.of(b1, b2));

        assertEquals(2,service.findAllFromUser(user).size());
        assertEquals(1,service.findAllVisibleFromUser(user).size());
    }

    @Test
    public void findByNameReturnsAllBooksWithSearchedWordInName() {
        final Book b1 = new Book();
        final Book b2 = new Book();
        final Book b3 = new Book();
        final Book b4 = new Book();

        b1.setName("name");
        b2.setName("name bla bla");
        b3.setName("bla bla name");
        b4.setName("nothing");

        em.persist(b1);
        em.persist(b2);
        em.persist(b3);
        em.persist(b4);

        service.persistAll(List.of(b1,b2,b3,b4));
        assertEquals(4,service.findAll().size());
        assertEquals(3,service.findByName("name").size());
    }

    @Test
    public void findVisibleByNameReturnsAllVisibleBooksWithSearchedWordInName() {
        final Book b1 = new Book();
        final Book b2 = new Book();
        final Book b3 = new Book();
        final Book b4 = new Book();

        b1.setName("name");
        b1.setVisible(true);
        b2.setName("name bla bla");
        b3.setName("bla bla name");
        b4.setName("nothing");

        em.persist(b1);
        em.persist(b2);
        em.persist(b3);
        em.persist(b4);

        service.persistAll(List.of(b1,b2,b3,b4));
        assertEquals(4,service.findAll().size());
        assertEquals(1,service.findVisibleByName("name").size());
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
