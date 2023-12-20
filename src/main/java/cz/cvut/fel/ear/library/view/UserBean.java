package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.*;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.rest.UserController;
import cz.cvut.fel.ear.library.security.SecurityUtils;
import cz.cvut.fel.ear.library.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.util.List;

@Component
@SessionScope
public class UserBean {
    private final BookController bookController;
    private final UserController userController;
    private final UserService userService;
    @Getter
    @Setter
    private String username = "";
    @Getter
    @Setter
    private String password = "";



    @Autowired
    public UserBean(BookController bookController, UserController userController, UserService userService) {
        this.bookController = bookController;
        this.userController = userController;
        this.userService = userService;
    }

    public User getCurrentUser() {
        return SecurityUtils.getCurrentUser();
    }

    public void saveData() {
        username = username;
        password = password;
    }

    public String login() throws ServletException, IOException {
        //do any job with the associated values that you've got from the user, like persisting attempted login, etc.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext extenalContext = facesContext.getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest)extenalContext.getRequest()).getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward((ServletRequest)extenalContext.getRequest(), (ServletResponse)extenalContext.getResponse());
        facesContext.responseComplete();
        return null;
    }


    public String handleLogIn() {
        if (getCurrentUser() != null) {
            return "./books.xhtml?faces-redirect=true";
        } else {
            if (username.equals("") || password.equals(""))
                return "";
            if (username.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Uživatelské jméno musí být vyplněno!"));
                return "";
            }
            if (password.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Heslo musí být vyplněno!"));
                return "";
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Špatné uživatelské jméno nebo heslo!"));
            return "";
        }

    }

    public boolean canEditBook(int bookId) {
        Book book = bookController.getBook(bookId);
        User user = getCurrentUser();
        if (user == null) return false;
        return (user == book.getUser() || userService.isUserAdmin(user));
    }

    public boolean canRenderBook(Book book) {
        if (book.getVisible()) return true;
        else {
            User user = getCurrentUser();
            if (user != null) return userService.isUserAdmin(user);
            else return false;
        }
    }

    public boolean isLogged() {
        return getCurrentUser() != null;
    }

    public String logout(String page) {
        SecurityContextHolder.clearContext(); //logout ze spring security
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

}
