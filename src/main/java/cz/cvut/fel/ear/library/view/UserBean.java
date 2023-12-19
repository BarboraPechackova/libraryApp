package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.rest.UserController;
import cz.cvut.fel.ear.library.security.model.UserDetails;
import cz.cvut.fel.ear.library.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;



@Component
@SessionScope
public class UserBean {
    private final BookController bookController;
    private final UserController userController;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private int userId = 0;
    private String username;
    private String password;



    @Autowired
    public UserBean(BookController bookController, UserController userController, UserService userService, PasswordEncoder passwordEncoder) {
        this.bookController = bookController;
        this.userController = userController;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    public String handleLogIn() {
        if
    }

    public User getUser() {
        return user;
    }


    public boolean canEditBook(int bookId) {
        Book book = bookController.getBook(bookId);
        if (userId == 0) return false;
        return (userId == book.getUser().getId() || userService.isUserAdmin(user));
    }

    public boolean canRenderBook(Book book) {
        if (book.getVisible()) return true;
        else {
            if (userId != 0) return userService.isUserAdmin(user);
            else return false;
        }
    }

    public boolean isLogged() {
        return userId != 0;
    }

    public String logout(String page) {
        SecurityContextHolder.clearContext(); //logout ze spring security
        user = null;
        userId = 0;
        return "./" + page + "?faces-redirect=true";
    }

    public String getColor(Role role) {
        return switch (role.getRole()) {
            case "USER" -> "default";
            case "ADMIN" -> "lightgreen";
            default -> "";
        };
    }

    public String getRole(Role role) {
        return switch (role.getRole()) {
            case "USER" -> "Běžný uživatel";
            case "ADMIN" -> "Administrátor";
            default -> "";
        };
    }

    public String bookLoanState(BookLoan bookLoan) {
        if (bookLoan.isReturned()) return "Vrácena";
        else return "Nevrácena";
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
