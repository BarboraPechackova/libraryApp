package cz.cvut.fel.ear.library.rest;

import cz.cvut.fel.ear.library.exceptions.NotFoundException;
import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;
import cz.cvut.fel.ear.library.rest.utils.RestUtils;
import cz.cvut.fel.ear.library.service.BookLoanService;
import cz.cvut.fel.ear.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/bookloans")
public class BookLoanController {

    private final BookLoanService service;
    private final BookService bookService;

    @Autowired
    public BookLoanController(BookLoanService service, BookService bookService) {
        this.service = service;
        this.bookService = bookService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookLoan> getBookLoans() {
        final List<BookLoan> bookLoans = service.findAll();
        if (bookLoans == null) {
            throw RestUtils.newNotFoundEx("BookLoans", 0);
        }
        return bookLoans;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookLoan getBookLoan(@PathVariable Integer id) {
        final BookLoan bookLoan = service.find(id);
        if (bookLoan == null) {
            throw RestUtils.newNotFoundEx("BookLoans", id);
        }
        return bookLoan;
    }



    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProducts(@PathVariable Integer id) {
        final Category category = service.find(id);
        if (category == null) {
            throw NotFoundException.create("Category", id);
        }
        final List<Product> products = productService.findAll(category);
        if (products == null) {
            throw NotFoundException.create("Products", id);
        }
        return products;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCategory(@RequestBody Category category) {
        service.persist(category);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", category.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addProductToCategory(@PathVariable int id, @RequestBody Product product) {
        final Category category = service.find(id);
        if (category == null) {
            throw NotFoundException.create("Category", id);
        }
        service.addProduct(category, product);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}/products/{idProducts}", category.getId(), product.getId());
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}/products/{idProdukty}")
    public ResponseEntity<Void> removeProductFromCategory(@PathVariable int id, @PathVariable int idProdukty) {
        final Category category = service.find(id);
        if (category == null) {
            throw NotFoundException.create("Category", id);
        }
        final Product product = productService.find(idProdukty);
        if (product == null) {
            throw NotFoundException.create("Product", id);
        }
        service.removeProduct(category, product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
