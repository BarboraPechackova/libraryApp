package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.model.*;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.service.BookCoverService;
import cz.cvut.fel.ear.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/books")
public class BookController {

    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooks() {
        final List<Book> books = service.findAll();
        if (books == null) {
            throw RestUtils.newNotFoundEx("Books",0);
        }
        return books;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getBook(@PathVariable Integer id) {
        final Book book = service.find(id);
        if (book == null) {
            throw RestUtils.newNotFoundEx("Books", id);
        }
        return book;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBook(@RequestBody(required = false) Book book) {
        service.persist(book);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/{id}", book.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody Book book) {
        service.update(book);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeBook(@PathVariable int id) {
        final Book book = service.find(id);
        if (book == null) {
            throw RestUtils.newNotFoundEx("Books", id);
        }
        service.remove(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/covers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookCover> getCoversOfBook(@PathVariable int id) {
        final Book book = service.find(id);
        if (book == null) {
            throw RestUtils.newNotFoundEx("Book", id);
        }
        return book.getBookCovers(); // TODO: determine if this is OK
    }

    @GetMapping(value = "/{id}/loans", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookLoan> getLoansOfBook(@PathVariable int id) {
        final Book book = service.find(id);
        if (book == null) {
            throw RestUtils.newNotFoundEx("Book", id);
        }
        return book.getBookLoans(); // TODO: determine if this is OK
    }

    @GetMapping(value = "/{id}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getReservationsOfBook(@PathVariable int id) {
        final Book book = service.find(id);
        if (book == null) {
            throw RestUtils.newNotFoundEx("Book", id);
        }
        return book.getReservations(); // TODO: determine if this is OK
    }

    @GetMapping(value = "/{id}/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> getRatingsOfBook(@PathVariable int id) {
        final Book book = service.find(id);
        if (book == null) {
            throw RestUtils.newNotFoundEx("Book", id);
        }
        return book.getRatings(); // TODO: determine if this is OK
    }
}
