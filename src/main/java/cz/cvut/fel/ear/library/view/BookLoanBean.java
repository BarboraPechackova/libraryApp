package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.rest.BookLoanController;
import cz.cvut.fel.ear.library.rest.ReservationController;
import cz.cvut.fel.ear.library.rest.UserController;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDate;

@Component
@RequestScope
public class BookLoanBean {

    private final BookLoanController bookLoanController;

    private final ReservationController reservationController;

    private final UserController userController;

    @Setter
    @Getter
    private LocalDate loanEndDate;

    @Autowired
    public BookLoanBean(BookLoanController bookLoanController, ReservationController reservationController, UserController userController) {
        this.bookLoanController = bookLoanController;
        this.reservationController = reservationController;
        this.userController = userController;
    }

    public void createBookLoan(Book book, User user) {
        if (!validateLoanConditions(user, book)) {
            return;
        }

        if (loanEndDate == null) {
            addErrorMessage("Musíte zadat datum vrácení");
            return;
        }

        if (!validateLoanDates(loanEndDate)) {
            return;
        }

        createLoanIfReservationExists(book, user);
    }

    public String returnBookLoanAndRefresh(int id) {
        bookLoanController.returnBookLoan(id);
        return "./user.xhtml?faces-redirect=true";
    }


    private boolean validateLoanConditions(User user, Book book) {
        if (userController.hasUnreturnedLoansOfUserBook(user.getId(), book.getId())) {
            addErrorMessage("Uživatel má již tuto knihu vypůjčenou, nelze udělat rezervace");
            return false;
        }
        return true;
    }

    private boolean validateLoanDates(LocalDate endDate) {
        if (endDate.isBefore(LocalDate.now().plusDays(1))) {
            addErrorMessage("Výpůjčka nemůže trvat méně než jeden den");
            return false;
        }
        if (endDate.isAfter(LocalDate.now().plusMonths(1))) {
            addErrorMessage("Výpůjčka nemůže trvat déle než měsíc");
            return false;
        }
        return true;
    }

    private void createLoanIfReservationExists(Book book, User user) {
        ResponseEntity<Reservation> response = reservationController.createReservation(book, user);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            Reservation reservation = response.getBody();
            bookLoanController.createBookLoanWithDates(reservation, LocalDate.now(), loanEndDate);
            addInfoMessage("Výpůjčka vytvořena");
        }
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }

    private void addInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
    }

}
