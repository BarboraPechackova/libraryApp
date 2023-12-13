package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.rest.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class UserBean {
    private String username;
    private String password;
    private int userId;
    private User user;
    private final BookController bookController;
    private final UserController userController;

    @Autowired
    public UserBean(BookController bookController, UserController userController) {
        this.bookController = bookController;
        this.userController = userController;
    }

    public boolean canEditBook(int bookId) {
        Book book = bookController.getBook(bookId);
        System.out.println(book.getUser().getUsername());
        return false;
    }

    public String login() {
        List<User> users = userController.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    userId = user.getId();
                    return "./books.xhtml";
                }
            }
        }
        System.out.println("Wrong username or password!");
//        System.out.println(username);
//        System.out.println(password);
        return "./";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
}
