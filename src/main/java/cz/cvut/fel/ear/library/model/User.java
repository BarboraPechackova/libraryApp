package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findByRole", query = "SELECT u from User u WHERE :role MEMBER OF u.roles"),
})
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "bank_account")
    private String bankAccount;

    @OneToMany
    private ArrayList<Role> roles;

    @OneToMany
    private ArrayList<Rating> ratings;

    @OneToMany
    private ArrayList<ProfilePicture> profilePictures;

    @OneToMany
    private ArrayList<Reservation> reservations;

    @OneToMany
    private ArrayList<BookLoan> bookLoans;

    @OneToMany
    private ArrayList<Book> books;  // books that the user offers or loans

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    public ArrayList<ProfilePicture> getProfilePictures() {
        return profilePictures;
    }

    public void setProfilePictures(ArrayList<ProfilePicture> profilePictures) {
        this.profilePictures = profilePictures;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public ArrayList<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(ArrayList<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (bankAccount != null ? !bankAccount.equals(user.bankAccount) : user.bankAccount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (bankAccount != null ? bankAccount.hashCode() : 0);
        return result;
    }
}
