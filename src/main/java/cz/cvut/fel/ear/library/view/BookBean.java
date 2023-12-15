package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.enums.BookState;
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
}
