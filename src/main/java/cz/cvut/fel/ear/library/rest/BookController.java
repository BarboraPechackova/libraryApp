package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.exceptions.NotFoundException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.model.Reservation;
import cz.cvut.fel.ear.library.service.BookLoanService;
import cz.cvut.fel.ear.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/books")
public class BookController {

    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}/loans", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookLoan> getLoansOfBook(@PathVariable int id) {
        final Book book = service.find(id);
        if (book == null) {
            throw NotFoundException.create("Book", id);
        }
        return book.getBookLoans(); // TODO: determine if this is OK
    }

    @GetMapping(value = "/{id}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getReservationsOfBook(@PathVariable int id) {
        final Book book = service.find(id);
        if (book == null) {
            throw NotFoundException.create("Book", id);
        }
        return book.getReservations(); // TODO: determine if this is OK
    }
}
