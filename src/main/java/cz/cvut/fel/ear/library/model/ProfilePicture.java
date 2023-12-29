package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "profile_picture")
@Getter
@Setter
public class ProfilePicture extends Picture {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "ts_from")
    private Timestamp tsFrom;

    @Basic
    @Column(name = "ts_to")
    private Timestamp tsTo;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public ProfilePicture(User user, byte[] picture) {
        super(picture);
    }

    public ProfilePicture() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilePicture that = (ProfilePicture) o;

        if (id != that.id) return false;
        if (user.getId() != that.user.getId()) return false;
        if (tsFrom != null ? !tsFrom.equals(that.tsFrom) : that.tsFrom != null) return false;
        if (tsTo != null ? !tsTo.equals(that.tsTo) : that.tsTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tsFrom != null ? tsFrom.hashCode() : 0);
        result = 31 * result + (tsTo != null ? tsTo.hashCode() : 0);
        result = 31 * result + user.getId();
        return result;
    }
}
