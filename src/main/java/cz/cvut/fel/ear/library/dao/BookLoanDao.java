package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Book;
import cz.cvut.fel.ear.library.model.BookLoan;

import cz.cvut.fel.ear.library.model.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class BookLoanDao extends BaseDao<BookLoan>{
    public BookLoanDao() {
        super(BookLoan.class);
    }

    public BookLoan getCurrentLoanOfBook(Book book) {
        TypedQuery<BookLoan> q = em.createNamedQuery("BookLoan.currentBookLoan", BookLoan.class);
        q.setParameter("book", book);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<BookLoan> getBookLoans(Book book) {
        TypedQuery<BookLoan> q = em.createNamedQuery("BookLoan.loansOfBook", BookLoan.class);
        q.setParameter("book", book);
        return q.getResultList();
    }

    public List<BookLoan> getUserLoans(User user) {
        TypedQuery<BookLoan> q = em.createNamedQuery("BookLoan.userBookLoans", BookLoan.class);
        q.setParameter("user", user);
        return q.getResultList();
    }

    public List<BookLoan> findActiveLoansByUserAndBook(User user, int bookId) {
        TypedQuery<BookLoan> query = em.createNamedQuery("BookLoan.activeLoansByUserAndBook", BookLoan.class);
        query.setParameter("user", user);
        query.setParameter("bookId", bookId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
