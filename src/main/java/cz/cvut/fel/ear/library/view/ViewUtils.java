package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.model.BookCover;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;

public final class ViewUtils {

    public static String getBookCoverImageUrl(BookCover bookCover) {
        if (bookCover == null)
            return "https://picsum.photos/200/300";  //default picture
        else {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String baseUrl = request.getRequestURL().toString();
            baseUrl = baseUrl.substring(0, baseUrl.length() - request.getRequestURI().length() + request.getContextPath().length());
            return baseUrl + "/rest/v1/pictures/covers/" + bookCover.getId();
        }
    }
}
