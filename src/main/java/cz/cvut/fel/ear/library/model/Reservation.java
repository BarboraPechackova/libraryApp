package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@NamedQueries({
        @NamedQuery(name = "Reservation.reservationsOfBook",    query = "SELECT r FROM Reservation r WHERE book = :book"),
        @NamedQuery(name = "Reservation.userBookReservations",  query = "SELECT r FROM Reservation r WHERE book = :book AND user = :user"),
//        @NamedQuery(name = "Reservation.actualBookReservations", query = "SELECT r FROM Reservation r WHERE book = :book AND status = :status"),
        @NamedQuery(name = "Reservation.allUserReservations", query = "SELECT r FROM Reservation r WHERE user = :user")
//        @NamedQuery(name = "Reservation.actualBookReservations", query = "SELECT r FROM Reservation r WHERE book = :book AND CURRENT_DATE BETWEEN dateFrom AND dateTo ")
})
public class Reservation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "reservation_ts")
    private Timestamp reservationTs;
    @Basic
    @Column(name = "date_from")
    private Date dateFrom;
    @Basic
    @Column(name = "date_to")
    private Date dateTo;
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id")
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getReservationTs() {
        return reservationTs;
    }

    public void setReservationTs(Timestamp reservationTs) {
        this.reservationTs = reservationTs;
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

        Reservation that = (Reservation) o;

        if (id != that.id) return false;
        if (user != that.user) return false;
        if (book != that.book) return false;
        if (reservationTs != null ? !reservationTs.equals(that.reservationTs) : that.reservationTs != null)
            return false;
        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null) return false;
        if (dateTo != null ? !dateTo.equals(that.dateTo) : that.dateTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (reservationTs != null ? reservationTs.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + user.getId();
        result = 31 * result + book.getId();
        return result;
    }
}
