package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.rest.BookLoanController;
import cz.cvut.fel.ear.library.rest.ReservationController;
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

    private LocalDate loanEndDate;

    @Autowired
    public BookLoanBean(BookLoanController bookLoanController, ReservationController reservationController) {
        this.bookLoanController = bookLoanController;
        this.reservationController = reservationController;
    }

    public void createBookLoan(Book book, User user) {
        ResponseEntity<Reservation> response = reservationController.createReservation(book, user);
        if (loanEndDate != null) {
            if (loanEndDate.isAfter(LocalDate.now())) {
                if (!loanEndDate.isAfter(LocalDate.now().plusMonths(1))) {
                    if (response.getStatusCode() == HttpStatus.CREATED) {
                        Reservation reservation = response.getBody();
                        System.out.println("there was reservation " + reservation);

                        // From now, ending in the date specified
                        bookLoanController.createBookLoanWithDates(reservation, LocalDate.now(), loanEndDate);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Výpůjčka vytvořena"));
                    }
                }
                else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Výpůjčka nemůže trvat déle než měsíc"));
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Datum nemůže být v minulosti"));
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Musíte zadat datum vrácení"));
        }
    }

    public LocalDate getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
    }



}
