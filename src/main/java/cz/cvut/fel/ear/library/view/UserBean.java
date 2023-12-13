package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.rest.BookController;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserBean {

    private BookController bookController;

    public boolean canEditBook(int bookId) {
        // if user is logged and is admin or owner of the book then it is true, else false
        return false;
    }

    public void login() {

    }
}
