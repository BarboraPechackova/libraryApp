package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rating.findByUser", query = "SELECT r from Rating r WHERE user.id = :idUser"),
        @NamedQuery(name = "Rating.findByBook", query = "SELECT r from Rating r WHERE book.id = :idBook")
})
@Getter
@Setter
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
