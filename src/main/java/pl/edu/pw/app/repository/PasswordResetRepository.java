package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.security.token.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
