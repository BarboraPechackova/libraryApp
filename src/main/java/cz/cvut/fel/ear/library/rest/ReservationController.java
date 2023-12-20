package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.enums.ReservationState;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.service.BookLoanService;
import cz.cvut.fel.ear.library.service.BookService;
import cz.cvut.fel.ear.library.service.ReservationService;
import cz.cvut.fel.ear.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rest/v1/reservations")
public class ReservationController {

    private final ReservationService service;
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService service, BookService bookService, UserService userService) {
        this.service = service;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getReservations() {
        final List<Reservation> reservations = service.findAll();
        if (reservations == null) {
            throw RestUtils.newNotFoundEx("Reservations", 0);
        }
        return reservations;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservation(@PathVariable Integer id) {
        final Reservation reservation = service.find(id);
        if (reservation == null) {
            throw RestUtils.newNotFoundEx("Reservations", id);
        }
        return reservation;
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
//        assert reservation != null;
//
//        service.persist(reservation);
//        final HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/{id}", reservation.getId());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> createReservation(@RequestBody Book book, @RequestBody User user) {
        assert book != null;
        assert user != null;
        Reservation reservation = new Reservation(user, book);
        service.persist(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        final Reservation reservation = service.find(id);
        if (reservation == null) {
            throw RestUtils.newNotFoundEx("Reservation", id);
        }

        // Set the state of the reservation to ZRUSENA
        reservation.setState(ReservationState.ZRUSENA);

        // Update the reservation
        service.update(reservation);

        // Return an appropriate response
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
