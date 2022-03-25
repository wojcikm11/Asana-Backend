package pl.edu.pw.security.token;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.app.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "password_reset_token")
@NoArgsConstructor
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static final int EXPIRATION = 60 * 24;


    private String token;


    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime expiresAt;


    public PasswordResetToken(String token, User user, LocalDateTime expiresAt) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }
}
