package pl.edu.pw.domain.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pw.security.config.PasswordSecurity;
import pl.edu.pw.security.token.ConfirmationToken;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name="user")
@Setter
@Getter
@EqualsAndHashCode
public class User implements PasswordSecurity, UserDetails {
    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length=50, unique = true)
    private String email;

    @Column(nullable = false, length=40)
    private String password;

    @Column(nullable = false, length=30)
    private String name;

    @Column
    private boolean locked;

    @Column
    private boolean enabled;



    @Override
    public String encrypt(String password) {
        return null;
    }

    public User(String email, String password, String name) {
        this.email=email;
        this.password=password;
        this.name=name;
        this.locked = false;
        this.enabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
