package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.ReservationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class BookReservationBean {

    private final ReservationController reservationController;

    @Autowired
    public BookReservationBean(ReservationController reservationController) {
        this.reservationController = reservationController;
    }

    public void createBookReservation(Book book, User user) {
        reservationController.createReservation(book, user);
    }
}
