package cz.cvut.fel.ear.library.view;

import cz.cvut.fel.ear.library.rest.BookController;
import cz.cvut.fel.ear.library.rest.BookLoanController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class BookLoanBean {

    private final BookLoanController bookLoanController;

    @Autowired
    public BookLoanBean(BookLoanController bookLoanController) {
        this.bookLoanController = bookLoanController;
    }

    public void createBookLoan() {
//        bookLoanController.createBookLoanWithDates();
    }



}
