package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book_cover")
@NamedQueries({
        @NamedQuery(name = "Cover.findByBook", query = "SELECT bc from BookCover bc WHERE book.id = :idBook")
})
public class BookCover extends Picture {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @OneToOne
    private Picture picture;

    @ManyToOne
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPicture() {
        return picture.getId();
    }

    public void setIdPicture(int idPicture) {
        picture.setId(idPicture);
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

        BookCover bookCover = (BookCover) o;

        if (id != bookCover.id) return false;
        if (picture.getId() != bookCover.picture.getId()) return false;
        if (book.getId() != bookCover.book.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + picture.getId();
        result = 31 * result + book.getId();
        return result;
    }
}
