package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.UserController;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class RegistrationBean {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password1;
    @Getter
    @Setter
    private String password2;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String surname;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String bankAccount;

    private final UserController userController;

    @Autowired
    public RegistrationBean(UserController userController) {
        this.userController = userController;
    }

    public String createUser() {
        if (validate()) {
            User user = new User(username,password1,firstName,surname,email,phone,bankAccount);
            userController.createUser(user);
            return "./login.xhtml?faces-redirect=true";
        } else {
            return "";
        }

    }

    public boolean validate() {
        boolean result = true;

        // unique username
        List<User> users = userController.getUsers();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Message Content."));
                return false;
            }
        }

        // e-mail contains @


        // passwords are the same


        // password is at least 8 chars long


        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Message Content."));
        return result;
    }
}
