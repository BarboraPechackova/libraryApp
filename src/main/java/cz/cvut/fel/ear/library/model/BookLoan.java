package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "book_loan")
@NamedQueries({
        @NamedQuery(name = "BookLoan.loansOfBook",    query = "SELECT l FROM BookLoan l WHERE book = :book"),
        @NamedQuery(name = "BookLoan.userBookLoans",  query = "SELECT l FROM BookLoan l WHERE user = :user"),
        @NamedQuery(name = "BookLoan.currentBookLoan", query = "SELECT l FROM BookLoan l WHERE book = :book AND returned = FALSE "),
        @NamedQuery(name = "BookLoan.getAllUserBookLoans", query = "SELECT l FROM BookLoan l WHERE book = :book AND user = :user")
//        @NamedQuery(name = "BookLoan.loanWithBookIdExists", query = "SELECT l FROM BookLoan l WHERE id_book = :idBook"),
})
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

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
