package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class SearchBean {
    private List<Book> books;
    private String searched;
    private final BookService bookService;

    @Autowired
    public SearchBean(BookService bookService) {
        this.bookService = bookService;
        books = bookService.findAllVisible();
        searched = "";
    }

    public List<Book> searchByName() {
        books = bookService.findByName(searched);
        return books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getSearched() {
        return searched;
    }

    public void setSearched(String searched) {
        this.searched = searched;
    }
}
