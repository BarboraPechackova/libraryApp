package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookCover;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.List;

@Component
@SessionScope
public class BookBean implements Serializable {

    @Autowired
    private Environment environment;
    private int selectedBookId;

    // Getter and setter for selectedBookId

    public void setSelectedBookId(int bookId) {
        selectedBookId = bookId;
    }

    public String setBookTestAndRedirect(int bookId) {
        selectedBookId = bookId;
        return "/public/bookDetail.xhtml?faces-redirect=true";
    }

    public int getSelectedBookId() {
        return selectedBookId;
    }


    public String getBookCoverURL(Book book) {
        List<BookCover> covers = book.getBookCovers();
        if (covers.isEmpty())
            return "https://picsum.photos/200/300";
        else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            String baseUrl = request.getRequestURL().toString();
            baseUrl = baseUrl.substring(0, baseUrl.length() - request.getRequestURI().length() + request.getContextPath().length()) + "/";
            String s = baseUrl + "rest/v1/pictures/covers/" + covers.get(0).getId();
            return s;
        }
    }
}
