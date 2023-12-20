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

    private LocalDate loanEndDate;

    @Autowired
    public BookLoanBean(BookLoanController bookLoanController, ReservationController reservationController, UserController userController) {
        this.bookLoanController = bookLoanController;
        this.reservationController = reservationController;
        this.userController = userController;
    }

    public void createBookLoan(Book book, User user) {

        if (userController.hasUnreturnedLoansOfUserBook(user.getId(), book.getId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Uživatel má již tuto knihu vypůjčenou, nelze udělat rezervace"));
            return;
        }
        if (loanEndDate != null) {
            if (!loanEndDate.isBefore(LocalDate.now().plusWeeks(1))) {
                if (!loanEndDate.isAfter(LocalDate.now().plusMonths(1))) {
                    ResponseEntity<Reservation> response = reservationController.createReservation(book, user);
                    if (response.getStatusCode() == HttpStatus.CREATED) {
                        Reservation reservation = response.getBody();
                        // Create book loan, from today until the date specified by the user
                        bookLoanController.createBookLoanWithDates(reservation, LocalDate.now(), loanEndDate);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Výpůjčka vytvořena"));
                    }
                }
                else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Výpůjčka nemůže trvat déle než měsíc"));
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Výpůjčka nemůže trvat méně než týden"));
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Musíte zadat datum vrácení"));
        }
    }

    public String returnBookLoanAndRefresh(int id) {
        bookLoanController.returnBookLoan(id);
        return "./user.xhtml?faces-redirect=true";
    }

    public LocalDate getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
    }



}
