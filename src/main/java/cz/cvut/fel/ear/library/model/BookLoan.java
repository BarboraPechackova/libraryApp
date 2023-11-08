package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "book_loan", schema = "public", catalog = "ear2023zs_2")
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
    @Basic
    @Column(name = "id_user")
    private int idUser;
    @Basic
    @Column(name = "id_book")
    private int idBook;

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookLoan bookLoan = (BookLoan) o;

        if (id != bookLoan.id) return false;
        if (price != bookLoan.price) return false;
        if (returned != bookLoan.returned) return false;
        if (idUser != bookLoan.idUser) return false;
        if (idBook != bookLoan.idBook) return false;
        if (dateFrom != null ? !dateFrom.equals(bookLoan.dateFrom) : bookLoan.dateFrom != null) return false;
        if (dateTo != null ? !dateTo.equals(bookLoan.dateTo) : bookLoan.dateTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (returned ? 1 : 0);
        result = 31 * result + idUser;
        result = 31 * result + idBook;
        return result;
    }
}
