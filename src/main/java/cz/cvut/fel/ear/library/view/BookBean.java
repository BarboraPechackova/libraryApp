package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.User;
import cz.cvut.fel.ear.library.model.BookCover;
import cz.cvut.fel.ear.library.model.enums.BookState;
import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.util.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class BookBean implements Serializable {

    private int selectedBookId;
    private int bookId;
    private String name;
    private String author;
    private String description;
    private int price;
    private String isbn;
    private boolean visible = true;
    private final BookController bookController;

    @Autowired
    public BookBean(BookController bookController) {
        this.bookController = bookController;
    }

    public String addBook(User user) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        book.setIsbn(isbn);
        book.setVisible(visible);
        book.setState(BookState.VOLNA);
        book.setUser(user);
        bookController.createBook(book);
        resetBookDetails();
        return "./user.xhtml?faces-redirect=true";
    }

    public String editBook(User user) {
        Book book = bookController.getBook(bookId);
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        book.setIsbn(isbn);
        book.setVisible(visible);
        book.setUser(user);
        bookController.updateBook(book);
        resetBookDetails();
        return "./books.xhtml?faces-redirect=true";
    }

    public String redirectToEditBook(Book book) {
        bookId = book.getId();
        name = book.getName();
        author = book.getAuthor();
        description = book.getDescription();
        price = book.getPrice();
        isbn = book.getIsbn();
        visible = book.getVisible();
        return "./editBook.xhtml?faces-redirect=true";
    }

    public String deleteBook(Book book) {
        bookController.removeBook(book.getId());
        return "./user.xhtml?faces-redirect=true";
    }

    public void resetBookDetails() {
        name = author = description = isbn = "";
        price = 0;
        visible = true;
    }

    // Getter and setter for selectedBookId

    public void setSelectedBookId(int bookId) {
        selectedBookId = bookId;
    }

    public String setBookTestAndRedirect(int bookId) {
        selectedBookId = bookId;
        return "/public/bookDetail.xhtml?faces-redirect=true";
    }

    public String getBookState(Book book) {
        if (book.getState() == BookState.VOLNA) return "Volná";
        if (book.getState() == BookState.REZERVOVANA) return "Rezervovaná";
        if (book.getState() == BookState.VYPUJCENA) return "Vypůjčená";
        return "";
    }

    public String getBookVisible(Book book) {
        if (book.getVisible()) return "Ano";
        else return "Ne";
    }

    public int getSelectedBookId() {
        return selectedBookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
}
