package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rating.findByUser", query = "SELECT r from Rating r WHERE user.id = :idUser"),
        @NamedQuery(name = "Rating.findByBook", query = "SELECT r from Rating r WHERE book.id = :idBook")
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
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdBook() {
        return book.getId();
    }

    public void setIdBook(int idBook) {
        book.setId(idBook);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (id != rating.id) return false;
        if (points != rating.points) return false;
        if (user.getId() != rating.user.getId()) return false;
        if (book.getId() != rating.book.getId()) return false;
        if (note != null ? !note.equals(rating.note) : rating.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) points;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + user.getId();
        result = 31 * result + book.getId();
        return result;
    }
}
