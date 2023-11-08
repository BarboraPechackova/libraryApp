package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rating.findByUser", query = "SELECT r from Rating r WHERE idUser = :idUser"),
        @NamedQuery(name = "Rating.findByBook", query = "SELECT r from Rating r WHERE idBook = :idBook")
})
public class Rating {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "points")
    private short points;
    @Basic
    @Column(name = "note")
    private String note;
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

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

        Rating rating = (Rating) o;

        if (id != rating.id) return false;
        if (points != rating.points) return false;
        if (idUser != rating.idUser) return false;
        if (idBook != rating.idBook) return false;
        if (note != null ? !note.equals(rating.note) : rating.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) points;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + idUser;
        result = 31 * result + idBook;
        return result;
    }
}
