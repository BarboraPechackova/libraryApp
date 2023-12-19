package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.rest.UserController;
import cz.cvut.fel.ear.library.security.model.UserDetails;
import cz.cvut.fel.ear.library.service.UserService;
import cz.cvut.fel.ear.library.service.security.UserDetailsService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import static org.primefaces.component.keyboard.KeyboardBase.PropertyKeys.password;

@Component
@SessionScope
public class UserBean {
    private final BookController bookController;
    private final UserController userController;
    private final UserService userService;
    private String username;
    private String password;

    // if userId is 0, then the user is not logged in

    @Autowired
    public UserBean(BookController bookController, UserController userController, UserService userService) {
        this.bookController = bookController;
        this.userController = userController;
        this.userService = userService;
    }

    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth.getPrincipal() instanceof UserDetails;
        UserDetails details;
        try {
            details = (UserDetails) auth.getPrincipal();
        } catch (Exception e) {
            return new User("","","","","","","");
        }
        return details.getUser();
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    public int getUserId() {
        return getUser().getId();
    }

    public boolean canEditBook(int bookId) {
        Book book = bookController.getBook(bookId);
        User user = getUser();
        if (user.getId() == 0) return false;
        return (user.getId() == book.getUser().getId() || userService.isUserAdmin(user));
    }

    public boolean canRenderBook(Book book) {
        if (book.getVisible()) return true;
        else {
            User user = getUser();
            if (user.getId() != 0) return userService.isUserAdmin(user);
            else return false;
        }
    }

    public boolean isLogged() {
        return getUser() != null;
    }


    // Hodne z toho bude tohoto bude delano pres sping security :-)

//    public String login() {
//        if (username.equals("")) FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Uživatelské jméno musí být vyplněno!"));
//        if (password.equals("")) FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Heslo musí být vyplněno!"));
//        if (username.equals("") || password.equals("")) return "";
//
//        User user = userService.findByUsername(username);
////        userDetailsService.loadUserByUsername(username);
//        if (passwordEncoder.matches(password, user.getPassword())) {
//            userId = user.getId();
//            this.user = user;
//            username = password = "";
//            return "./books.xhtml?faces-redirect=true";
//        }
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Špatné uživatelské jméno nebo heslo!"));
//        return "";
//    }

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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
