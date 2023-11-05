package cz.cvut.fel.ear.library;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "profile_picture", schema = "public", catalog = "ear2023zs_2")
public class ProfilePicture {
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
    @Basic
    @Column(name = "id_picture")
    private int idPicture;
    @Basic
    @Column(name = "id_user")
    private int idUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTsFrom() {
        return tsFrom;
    }

    public void setTsFrom(Timestamp tsFrom) {
        this.tsFrom = tsFrom;
    }

    public Timestamp getTsTo() {
        return tsTo;
    }

    public void setTsTo(Timestamp tsTo) {
        this.tsTo = tsTo;
    }

    public int getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(int idPicture) {
        this.idPicture = idPicture;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilePicture that = (ProfilePicture) o;

        if (id != that.id) return false;
        if (idPicture != that.idPicture) return false;
        if (idUser != that.idUser) return false;
        if (tsFrom != null ? !tsFrom.equals(that.tsFrom) : that.tsFrom != null) return false;
        if (tsTo != null ? !tsTo.equals(that.tsTo) : that.tsTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tsFrom != null ? tsFrom.hashCode() : 0);
        result = 31 * result + (tsTo != null ? tsTo.hashCode() : 0);
        result = 31 * result + idPicture;
        result = 31 * result + idUser;
        return result;
    }
}
