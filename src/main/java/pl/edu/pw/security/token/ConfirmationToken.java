package pl.edu.pw.security.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pw.app.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false, name="created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name="expires_at")
    private LocalDateTime expiredAt;

    @Column(name="confirmed_at")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable=false, name="user_id")
    private User user;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt,User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }
}
