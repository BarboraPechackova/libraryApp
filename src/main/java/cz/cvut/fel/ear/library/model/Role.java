package cz.cvut.fel.ear.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQueries({
        @NamedQuery(name = "Role.findAllAdmins", query = "SELECT r.user FROM Role r WHERE r.role = 'ADMIN'"),
        @NamedQuery(name = "Role.findAllBasicUsers", query = "SELECT r.user FROM Role r WHERE r.role = 'USER'"),
        @NamedQuery(name = "Role.findAllUsersByRoleName", query = "SELECT r.user FROM Role r WHERE r.role = :name"),
        @NamedQuery(name = "Role.findAllRolesOfUser", query = "SELECT r FROM Role r WHERE r.user = :user")
})
@Getter
@Setter
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        if (id != role1.id) return false;
        if (user.getId() != role1.user.getId()) return false;
        if (role != null ? !role.equals(role1.role) : role1.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + user.getId();
        return result;
    }
}
