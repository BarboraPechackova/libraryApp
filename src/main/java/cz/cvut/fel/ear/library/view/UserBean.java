package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.*;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.rest.UserController;
import cz.cvut.fel.ear.library.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    // if userId is 0, then the user is not logged in

    @Autowired
    public UserBean(BookController bookController, UserController userController, UserService userService, PasswordEncoder passwordEncoder) {
        this.bookController = bookController;
        this.userController = userController;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
        return userId!=0;
    }

    // TODO jak je to s hashovanim?

    public String login() {
        if (username.equals("")) FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Uživatelské jméno musí být vyplněno!"));
        if (password.equals("")) FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Heslo musí být vyplněno!"));
        if (username.equals("") || password.equals("")) return "";
//        List<User> users = userController.getUsers();
        User user = userService.findByUsername(username);
        if (passwordEncoder.matches(password, user.getPassword())) {
            userId = user.getId();
            this.user = user;
            username = password = "";
            return "./books.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Špatné uživatelské jméno nebo heslo!"));
        return "";
    }

    public String logout(String page) {
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

    public String bookReservationState(Reservation reservation) {
        return switch (reservation.getState()) {
            case AKTIVNI -> "Aktivní";
            case ZRUSENA -> "Zrušená";
            case VYDANA -> "Vydaná";
        };
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

    public int getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
    }
}
