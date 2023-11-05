package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
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

        Reservation that = (Reservation) o;

        if (id != that.id) return false;
        if (idUser != that.idUser) return false;
        if (idBook != that.idBook) return false;
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
        result = 31 * result + idUser;
        result = 31 * result + idBook;
        return result;
    }
}
