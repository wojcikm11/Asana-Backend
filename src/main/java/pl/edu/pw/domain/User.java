package pl.edu.pw.domain;

import lombok.*;
import pl.edu.pw.domain.security.PasswordSecurity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Entity
@Table(name="user")
public class User implements PasswordSecurity {
    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length=50, unique = true)
    private String email;

    @Column(nullable = false, length=40)
    private String password;

    @Column(nullable = false, length=30)
    private String name;

    @Override
    public String encrypt(String password) {
        return null;
    }

    public User(String email, String password, String name){
        this.email=email;
        this.password=password;
        this.name=name;
    }
}
