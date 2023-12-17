package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.model.*;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.service.BookCoverService;
import cz.cvut.fel.ear.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/pictures")
public class PictureController {

    private final BookService bookService;
    private final BookCoverService coverService;
//    private final ProfilePictureService profileService;

    @Autowired
    public PictureController(BookService bookService, BookCoverService coverService) {
        this.bookService = bookService;
        this.coverService = coverService;
    }

//    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Book> getBooks() {
//        final List<Book> books = service.findAll();
//        if (books == null) {
//            throw RestUtils.newNotFoundEx("Books",0);
//        }
//        return books;
//    }

    @GetMapping(value = "/covers/{id}")
    public byte[] getBookCover(@PathVariable Integer id)  {
        final BookCover cover = coverService.find(id);
        if (cover == null) {
            throw RestUtils.newNotFoundEx("BookCover", id);
        }
        return cover.getPicture();
    }

    @PostMapping(value = "/covers/")
    public ResponseEntity<Void> newBookCover(@RequestBody BookCover cover)  {
        coverService.persist(cover);
        HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/covers/{id}", cover.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/covers/{id}")
    public void deleteBookCover(@PathVariable Integer id)  {
        final BookCover cover = coverService.find(id);
        if (cover == null) {
            throw RestUtils.newNotFoundEx("BookCover", id);
        }

        coverService.delete(cover);

    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> createBook(@RequestBody(required = false) Book book) {
//        service.persist(book);
//        HttpHeaders headers = RestUtils.createLocationHeaderFromUri("/{id}", book.getId());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> removeBook(@PathVariable int id) {
//        final Book book = service.find(id);
//        if (book == null) {
//            throw RestUtils.newNotFoundEx("Books", id);
//        }
//        service.remove(book);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping(value = "/{id}/covers", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<BookCover> getCoversOfBook(@PathVariable int id) {
//        final Book book = service.find(id);
//        if (book == null) {
//            throw RestUtils.newNotFoundEx("Book", id);
//        }
//        return book.getBookCovers(); // TODO: determine if this is OK
//    }
//
//    @GetMapping(value = "/{id}/loans", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<BookLoan> getLoansOfBook(@PathVariable int id) {
//        final Book book = service.find(id);
//        if (book == null) {
//            throw RestUtils.newNotFoundEx("Book", id);
//        }
//        return book.getBookLoans(); // TODO: determine if this is OK
//    }
//
//    @GetMapping(value = "/{id}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Reservation> getReservationsOfBook(@PathVariable int id) {
//        final Book book = service.find(id);
//        if (book == null) {
//            throw RestUtils.newNotFoundEx("Book", id);
//        }
//        return book.getReservations(); // TODO: determine if this is OK
//    }
//
//    @GetMapping(value = "/{id}/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Rating> getRatingsOfBook(@PathVariable int id) {
//        final Book book = service.find(id);
//        if (book == null) {
//            throw RestUtils.newNotFoundEx("Book", id);
//        }
//        return book.getRatings(); // TODO: determine if this is OK
//    }
}
