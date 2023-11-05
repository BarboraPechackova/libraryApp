package cz.cvut.fel.ear.library;

import jakarta.persistence.*;

@Entity
@Table(name = "book_cover", schema = "public", catalog = "ear2023zs_2")
public class BookCover {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_picture")
    private int idPicture;
    @Basic
    @Column(name = "id_book")
    private int idBook;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(int idPicture) {
        this.idPicture = idPicture;
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

        BookCover bookCover = (BookCover) o;

        if (id != bookCover.id) return false;
        if (idPicture != bookCover.idPicture) return false;
        if (idBook != bookCover.idBook) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idPicture;
        result = 31 * result + idBook;
        return result;
    }
}
