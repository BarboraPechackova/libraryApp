package cz.cvut.fel.ear.library.model;

import cz.cvut.fel.ear.library.model.enums.BookState;
import cz.cvut.fel.ear.library.model.enums.ReservationState;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Reservation.reservationsOfBook",    query = "SELECT r FROM Reservation r WHERE book = :book"),
        @NamedQuery(name = "Reservation.userBookReservations",  query = "SELECT r FROM Reservation r WHERE book = :book AND user = :user"),
        @NamedQuery(name = "Reservation.activeBookReservations", query = "SELECT r FROM Reservation r WHERE book = :book AND state = 'AKTIVNI'"),
        @NamedQuery(name = "Reservation.activeUserReservations", query = "SELECT r FROM Reservation r WHERE book = :book AND state = 'AKTIVNI'"),
        @NamedQuery(name = "Reservation.allUserReservations", query = "SELECT r FROM Reservation r WHERE user = :user")
//        @NamedQuery(name = "Reservation.actualBookReservations", query = "SELECT r FROM Reservation r WHERE book = :book AND CURRENT_DATE BETWEEN dateFrom AND dateTo ")
})
public class Reservation {

    /**
     * Udelat enum se stavem rezervace (rezervovana, stornovana(majitelem knihy), uspokojena)
     * zruseni ts_from a ts_to
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "reservation_ts")
    private Timestamp reservationTs;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ReservationState state;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;

    /**
     * Sets default values pre persist.
     */
    @PrePersist
    public void prePersist() {
        if (reservationTs == null) {
            reservationTs = Timestamp.valueOf(LocalDateTime.now());
        }
        if (state == null) {
            state = ReservationState.AKTIVNI;
        }
    }

    public Reservation(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public Reservation() {}

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

    public ReservationState getState() {
        return state;
    }

    public void setState(ReservationState state) {
        this.state = state;
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
        if (state != that.state) return false;
        if (reservationTs != null ? !reservationTs.equals(that.reservationTs) : that.reservationTs != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (reservationTs != null ? reservationTs.hashCode() : 0);
        result = 31 * result + user.getId();
        result = 31 * result + book.getId();
        result = 31 * result + state.hashCode();
        return result;
    }
}
