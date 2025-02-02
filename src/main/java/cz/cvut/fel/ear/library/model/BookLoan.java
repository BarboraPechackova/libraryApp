package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "book_loan")
@NamedQueries({
        @NamedQuery(name = "BookLoan.loansOfBook",    query = "SELECT l FROM BookLoan l WHERE book = :book"),
        @NamedQuery(name = "BookLoan.userBookLoans",  query = "SELECT l FROM BookLoan l WHERE user = :user"),
        @NamedQuery(name = "BookLoan.currentBookLoan", query = "SELECT l FROM BookLoan l WHERE book = :book AND returned = FALSE "),
        @NamedQuery(name = "BookLoan.getAllUserBookLoans", query = "SELECT l FROM BookLoan l WHERE book = :book AND user = :user"),
        @NamedQuery(name = "BookLoan.activeLoansByUserAndBook", query = "SELECT l FROM BookLoan l WHERE l.user = :user AND l.book.id = :bookId AND l.returned = false")
})
@Getter
@Setter
public class BookLoan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "date_from")
    private Date dateFrom;

    @Basic
    @Column(name = "date_to")
    private Date dateTo;

    @Basic
    @Column(name = "price")
    private int price;

    @Basic
    @Column(name = "returned")
    private boolean returned;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;

    public BookLoan(Date dateFrom, Date dateTo, User user, Book book) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.price = book.getPrice();
        this.returned = false;
        this.user = user;
        this.book = book;
    }

    public BookLoan() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookLoan bookLoan = (BookLoan) o;

        if (id != bookLoan.id) return false;
        if (price != bookLoan.price) return false;
        if (returned != bookLoan.returned) return false;
        if (user != bookLoan.user) return false;
        if (book != bookLoan.book) return false;
        if (!Objects.equals(dateFrom, bookLoan.dateFrom)) return false;
        if (!Objects.equals(dateTo, bookLoan.dateTo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (returned ? 1 : 0);
        result = (31 * result) + user.getId();
        result = (31 * result) + book.getId();
        return result;
    }
}
