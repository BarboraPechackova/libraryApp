package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.BookLoan;
import org.springframework.stereotype.Repository;

@Repository
public class BookLoanDao extends BaseDao<BookLoan>{
    public BookLoanDao() {
        super(BookLoan.class);
    }
}
