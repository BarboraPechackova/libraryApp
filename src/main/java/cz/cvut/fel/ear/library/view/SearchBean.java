package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@SessionScope
public class SearchBean {
    private List<Book> books;
    private String searched;
    private final BookService bookService;

    @Autowired
    public SearchBean(BookService bookService) {
        this.bookService = bookService;
        books = bookService.findAll();
        searched = "";
    }

    public List<Book> searchByName() {
        if (searched.equals("")) {
            return bookService.findAll();
        }

        List<Book> toAdd = new ArrayList<>();

        books = bookService.findByName(searched);
        for (Book book : bookService.findByAuthor(searched)) {
            if (!contains(books, book)) {
                toAdd.add(book);
            }
        }

        books.addAll(toAdd);
//        books.addAll(bookService.findByAuthor(searched));

        searched = "";
        return books;
    }

    private boolean contains(List<Book> books, Book bookToCheck) {
        for (Book book : books) {
            if (book.getId() == bookToCheck.getId()) return true;
        }
        return false;
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
