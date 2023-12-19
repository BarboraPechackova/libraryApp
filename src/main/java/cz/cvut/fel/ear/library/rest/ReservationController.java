package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.security.model.UserDetails;
import cz.cvut.fel.ear.library.service.BookLoanService;
import cz.cvut.fel.ear.library.service.BookService;
import cz.cvut.fel.ear.library.service.ReservationService;
import cz.cvut.fel.ear.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasRole('ADMIN')")
    public List<Reservation> getReservations() {
        final List<Reservation> reservations = service.findAll();
        if (reservations == null) {
            throw RestUtils.newNotFoundEx("Reservations", 0);
        }
        return reservations;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Reservation getReservation(@PathVariable Integer id) {
        final Reservation reservation = service.find(id);
        if (reservation == null) {
            throw RestUtils.newNotFoundEx("Reservations", id);
        }
        return reservation;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> createReservation(@RequestBody Book book, @RequestBody User user) {
        assert book != null;
        assert user != null;
        Reservation reservation = new Reservation(user, book);
        service.persist(reservation);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/{id}", reservation.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public void removeReservation(@PathVariable int id, Authentication auth) {
        final Reservation reservation = service.find(id);
        if (reservation == null) {
            throw RestUtils.newNotFoundEx("Reservation", id);
        }


        assert auth.getPrincipal() instanceof UserDetails;
        final User user = ((UserDetails) auth.getPrincipal()).getUser();

        if (!userService.isUserAdmin(user) && reservation.getUser().getId() != user.getId()) {
            throw new AccessDeniedException("Cannot delete other users reservation!");
        }


    }


}
