package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.DemoApplication;
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
@ComponentScan(basePackageClasses = DemoApplication.class, excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class)
})
@ActiveProfiles("test")
public class UserDaoTest {

    // TestEntityManager provides additional test-related methods (it is Spring-specific)
    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserDao userDao;

    @Test
    public void findAllByCategoryReturnsProductsInSpecifiedCategory() {
        final User usr = generateUser(1, "john_doe", "john", "doe", "john.doe@gmail.com", "+420604444444", "2100000000/2010");

        final List<User> role = generateRole(role);
        final List<User> result = userDao.findAll(role);
        assertEquals(products.size(), result.size());
        usr.sort(Comparator.comparing(Product::getName));
        result.sort(Comparator.comparing(Product::getName));
        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i).getId(), result.get(i).getId());
        }
    }

    private User generateUser(int id, String username, String firstName, String surname, String email, String phone, String bankAccount) {
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