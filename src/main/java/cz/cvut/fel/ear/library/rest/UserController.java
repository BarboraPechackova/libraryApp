package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.model.*;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.enums.ReservationState;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.service.BookLoanService;
import cz.cvut.fel.ear.library.service.ReservationService;
import cz.cvut.fel.ear.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/users")
public class UserController {
    private final UserService service;

    private final ReservationService reservationService;

    private final BookLoanService bookLoanService;

    @Autowired
    public UserController(UserService service, ReservationService reservationService, BookLoanService bookLoanService) {
        this.service = service;
        this.reservationService = reservationService;
        this.bookLoanService = bookLoanService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        final List<User> users = service.findAll();
        if (users == null) {
            throw RestUtils.newNotFoundEx("Users",0);
        }
        return users;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Integer id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("Users", id);
        }
        return user;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody(required = false) User user) {
        service.persist(user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/{id}", user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable int id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("Users", id);
        }
        service.removeUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooksOfUser(@PathVariable int id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", id);
        }
        return user.getBooks();
    }

    @GetMapping(value = "/{id}/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> getRatingsOfUser(@PathVariable int id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", id);
        }
        return user.getRatings();
    }

    @GetMapping(value = "/{id}/loans", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookLoan> getLoansOfUser(@PathVariable int id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", id);
        }
        return user.getBookLoans();
    }

    @GetMapping(value = "/{id}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getReservationsOfUser(@PathVariable int id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", id);
        }
        return user.getReservations();
    }

    @GetMapping(value = "/{userId}/reservations/active/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean hasActiveReservationsOfBook(@PathVariable int userId, @PathVariable int bookId) {
        final User user = service.find(userId);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", userId);
        }
        return reservationService.hasActiveReservationsOfBook(user, bookId);
    }

    @GetMapping(value = "/{userId}/loans/active/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean hasUnreturnedLoansOfUserBook(@PathVariable int userId, @PathVariable int bookId) {
        final User user = service.find(userId);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", userId);
        }
        return bookLoanService.hasUnreturnedLoansOfUserBook(user, bookId);
    }


}
