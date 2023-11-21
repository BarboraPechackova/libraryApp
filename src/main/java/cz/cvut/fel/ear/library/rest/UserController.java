package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.model.*;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.service.UserService;
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

    public UserController(UserService service) {
        this.service = service;
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
        return user.getBooks(); // TODO: determine if this is OK
    }

    @GetMapping(value = "/{id}/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> getRatingsOfUser(@PathVariable int id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", id);
        }
        return user.getRatings(); // TODO: determine if this is OK
    }

    @GetMapping(value = "/{id}/loans", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookLoan> getLoansOfUser(@PathVariable int id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", id);
        }
        return user.getBookLoans(); // TODO: determine if this is OK
    }

    @GetMapping(value = "/{id}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getReservationsOfUser(@PathVariable int id) {
        final User user = service.find(id);
        if (user == null) {
            throw RestUtils.newNotFoundEx("User", id);
        }
        return user.getReservations(); // TODO: determine if this is OK
    }
}
