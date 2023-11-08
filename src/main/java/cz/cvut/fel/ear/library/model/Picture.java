package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Arrays;

@Entity
public class Picture {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "upload_ts")
    private Timestamp uploadTs;
    @Basic
    @Column(name = "picture")
    private byte[] picture;
    @Basic
    @Column(name = "type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getUploadTs() {
        return uploadTs;
    }

    public void setUploadTs(Timestamp uploadTs) {
        this.uploadTs = uploadTs;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Picture picture1 = (Picture) o;

        if (id != picture1.id) return false;
        if (uploadTs != null ? !uploadTs.equals(picture1.uploadTs) : picture1.uploadTs != null) return false;
        if (!Arrays.equals(picture, picture1.picture)) return false;
        if (type != null ? !type.equals(picture1.type) : picture1.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uploadTs != null ? uploadTs.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(picture);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
