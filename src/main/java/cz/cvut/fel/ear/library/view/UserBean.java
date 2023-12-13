package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserBean {
    private String username;
    private String password;
    private final BookController bookController;

    @Autowired
    public UserBean(BookController bookController) {
        this.bookController = bookController;
    }

    public boolean canEditBook(int bookId) {
        Book book = bookController.getBook(bookId);
        System.out.println(book.getUser().getUsername());
        return false;
    }

    public String login() {
        System.out.println(username);
        System.out.println(password);
        return "./books.xhtml";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
}
