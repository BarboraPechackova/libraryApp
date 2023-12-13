package cz.cvut.fel.ear.library.view;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
@Component
@SessionScope
public class BookBean implements Serializable {
    private int selectedBookId;

    // Getter and setter for selectedBookId

    public void setSelectedBookId(int bookId) {
        selectedBookId = bookId;
    }

    public String setBookTestAndRedirect(int bookId) {
        selectedBookId = bookId;
        System.out.println("Selected Book ID: " + selectedBookId);
        return "/public/bookDetail.xhtml?faces-redirect=true";
    }

    public int getSelectedBookId() {
        return selectedBookId;
    }
}
