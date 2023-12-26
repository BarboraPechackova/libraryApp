package cz.cvut.fel.ear.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.fel.ear.library.model.enums.BookState;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import jakarta.persistence.CascadeType;

import java.util.Collection;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Book.findVisibleOnly", query = "SELECT b from Book b WHERE b.visible = TRUE"),
        @NamedQuery(name = "Book.findByUser", query = "SELECT b from Book b WHERE user.id = :idUser"),
        @NamedQuery(name = "Book.findVisibleByUser", query = "SELECT b from Book b WHERE user.id = :idUser and visible = TRUE"),
        @NamedQuery(name = "Book.findByName", query = "SELECT b from Book b WHERE LOWER(b.name) LIKE LOWER(:name)"),
        @NamedQuery(name = "Book.findByAuthor", query = "SELECT b from Book b WHERE LOWER(b.author) LIKE LOWER(:author)"),
        @NamedQuery(name = "Book.findVisibleByName", query = "SELECT b from Book b WHERE LOWER(b.name) LIKE LOWER(:name) and visible=TRUE")
})
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;
    @Column(name = "isbn")
    private String isbn;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private BookState state;
    @Column(name = "visible")
    private Boolean visible;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OrderBy("dateTo desc")
    private List<BookLoan> bookLoans;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OrderBy("reservationTs asc")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OrderBy("uploadTs asc")
    private List<BookCover> bookCovers;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OrderBy("id desc")
    private List<Rating> ratings;

    /**
     * Sets default values pre persist.
     */
    @PrePersist
    public void prePersist() {
        if (state == null) {
            state = BookState.VOLNA;
        }
        if (visible == null) {
            visible = true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Object getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(List<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<BookCover> getBookCovers() {
        return bookCovers;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (price != book.price) return false;
        if (visible != book.visible) return false;
        if (user.getId() != book.user.getId()) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (description != null ? !description.equals(book.description) : book.description != null) return false;
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (state != null ? !state.equals(book.state) : book.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (visible ? 1 : 0);
        result = 31 * result + user.getId();
        return result;
    }

    @OneToMany(mappedBy = "book")
    private Collection<BookLoan> bookLoan;

    public Collection<BookLoan> getBookLoan() {
        return bookLoan;
    }

    public void setBookLoan(Collection<BookLoan> bookLoan) {
        this.bookLoan = bookLoan;
    }
}
