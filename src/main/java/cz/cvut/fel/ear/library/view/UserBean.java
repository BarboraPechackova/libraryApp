package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.rest.UserController;
import cz.cvut.fel.ear.library.service.UserService;
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
    private final UserService userService;

    // if userId is 0, then the user is not logged in

    @Autowired
    public UserBean(BookController bookController, UserController userController, UserService userService) {
        this.bookController = bookController;
        this.userController = userController;
        this.userService = userService;
    }

    public boolean canEditBook(int bookId) {
        Book book = bookController.getBook(bookId);
        if (userId == 0) return false;
        return (userId == book.getUser().getId() || userService.isUserAdmin(user));
    }

    public boolean isLogged() {
        return userId!=0;
    }

    // TODO jak je to s hashovanim?

    public String login() {
        List<User> users = userController.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    userId = user.getId();
                    this.user = user;
                    return "./books.xhtml?faces-redirect=true";
                }
            }
        }
        System.out.println("Wrong username or password!");
        return "./";
    }

    public String logout(String page) {
        user = null;
        userId = 0;
        return "./" + page + "?faces-redirect=true";
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
