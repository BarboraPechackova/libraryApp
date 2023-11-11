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
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;
//    @OneToOne
//    @JoinColumn(name = "id_picture")
//    private Picture picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (book.getId() != bookCover.book.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + book.getId();
        return result;
    }
}
