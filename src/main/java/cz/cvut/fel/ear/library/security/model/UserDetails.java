package cz.cvut.fel.ear.library.security.model;

import cz.cvut.fel.ear.library.model.Role;
import cz.cvut.fel.ear.library.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final User user;

    private final Set<GrantedAuthority> authorities;

    public UserDetails(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
    }

    public UserDetails(User user, Collection<GrantedAuthority> authorities) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(authorities);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
        this.authorities.addAll(authorities);
    }

    private void addUserRole() {
        String role = getHighestRoleOfUser(user);
        if(!role.startsWith("ROLE_")) {
            role = "ROLE_"+role;
        }
        authorities.add(new SimpleGrantedAuthority(role));
    }

    public String getHighestRoleOfUser(User user) {
        return user.getRoles().stream()
                .map(Role::getRole)
                .distinct()
                .filter(string -> string.equals("ADMIN"))
                .findFirst()
                .orElse(user.getRoles().stream()
                        .map(Role::getRole)
                        .distinct()
                        .filter(string -> string.equals("USER"))
                        .findFirst()
                        .orElse("GUEST")
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}
