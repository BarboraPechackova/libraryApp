package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;

import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BookLoanDao extends BaseDao<BookLoan>{
    public BookLoanDao() {
        super(BookLoan.class);
    }


    public boolean isBookLoaned(Book book) {
        Query q = em.createNamedQuery("BookLoan.actualBookLoan", BookLoan.class);
        q.setParameter("book", book);
        return q.getSingleResult() == null;
    }
}
