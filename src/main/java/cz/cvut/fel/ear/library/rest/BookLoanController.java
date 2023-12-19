package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.exceptions.BookAlreadyReturnedException;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.security.model.UserDetails;
import cz.cvut.fel.ear.library.service.BookLoanService;
import cz.cvut.fel.ear.library.service.BookService;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/bookloans")
public class BookLoanController {

    private final BookLoanService service;
    private final UserService userService;

    @Autowired
    public BookLoanController(BookLoanService service, BookService bookService, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookLoan> getBookLoans() {
        final List<BookLoan> bookLoans = service.findAll();
        if (bookLoans == null) {
            throw RestUtils.newNotFoundEx("BookLoans", 0);
        }
        return bookLoans;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public BookLoan getBookLoan(@PathVariable Integer id, Authentication auth) {
        final BookLoan bookLoan = service.find(id);
        if (bookLoan == null) {
            throw RestUtils.newNotFoundEx("BookLoans", id);
        }

        assert auth.getPrincipal() instanceof UserDetails;
        final User user = ((UserDetails) auth.getPrincipal()).getUser();

        if (!userService.isUserAdmin(user) && bookLoan.getUser().getId() != user.getId()) {
            throw new AccessDeniedException("Cannot access order of another customer");
        }
        return bookLoan;
    }



    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> createBookLoanWithDates(@RequestBody Reservation reservation, @RequestBody LocalDate dateFrom, @RequestBody LocalDate dateTo) {
        assert reservation != null;

        BookLoan bookLoan = service.makeBookLoanFromReservation(reservation, dateFrom, dateTo);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/{id}", bookLoan.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/return", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> returnBookLoan(@PathVariable Integer id) {
        final BookLoan bookLoan = service.find(id);
        if (bookLoan == null) {
            throw RestUtils.newNotFoundEx("BookLoans", id);
        }

        try {
            service.returnBookLoan(bookLoan);
        } catch (BookAlreadyReturnedException e) {
            return new ResponseEntity<>(RestUtils.createHttpHeaders(), HttpStatus.I_AM_A_TEAPOT);
        }

        return new ResponseEntity<>(RestUtils.createHttpHeaders(), HttpStatus.NO_CONTENT);
    }


}
