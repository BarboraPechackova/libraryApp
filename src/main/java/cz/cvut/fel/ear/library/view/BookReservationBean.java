package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.ReservationController;
import cz.cvut.fel.ear.library.rest.UserController;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class BookReservationBean {

    private final ReservationController reservationController;

    private final UserController userController;

    @Autowired
    public BookReservationBean(ReservationController reservationController, UserController userController) {
        this.reservationController = reservationController;
        this.userController = userController;
    }

    public void createBookReservation(Book book, User user) {
        if (!validateReservationConditions(user, book)) {
            return;
        }

        reservationController.createReservation(book, user);
        addInfoMessage("Rezervace vytvořena");
    }

    public String removeBookReservationAndRedirect(int id){
        reservationController.deleteReservation(id);
        return "./user.xhtml?faces-redirect=true";
    }


    private boolean validateReservationConditions(User user, Book book) {
        if (userController.hasActiveReservationsOfBook(user.getId(), book.getId())) {
            addErrorMessage("Uživatel má již aktivní rezervaci na tuto knihu");
            return false;
        }

        if (userController.hasUnreturnedLoansOfUserBook(user.getId(), book.getId())) {
            addErrorMessage("Nelze vytvořit rezervace na již vypůjčenou knihu");
            return false;
        }

        return true;
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }

    private void addInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
    }
}
