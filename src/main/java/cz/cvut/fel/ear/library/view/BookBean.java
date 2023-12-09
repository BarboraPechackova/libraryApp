package cz.cvut.fel.ear.library.view;

import jakarta.faces.view.ViewScoped;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ViewScoped
public class BookBean implements Serializable {
    private int selectedBookId;

    // Getter and setter for selectedBookId

    public void setSelectedBookId(int bookId) {
        selectedBookId = bookId;
    }

    public String setBookTest() {
        return "book";
    }

    public int getSelectedBookId() {
        return selectedBookId;

    }
}
