package cz.cvut.fel.ear.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Picture {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "upload_ts")
    private Timestamp uploadTs;

    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "type")
    private String type;

    public Picture(byte[] picture) {
        this.picture = picture;
    }

    public Picture() {
    }

    @PrePersist
    public void prePersist() {
        if (uploadTs == null) {
            uploadTs = Timestamp.valueOf(LocalDateTime.now());
        }
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
