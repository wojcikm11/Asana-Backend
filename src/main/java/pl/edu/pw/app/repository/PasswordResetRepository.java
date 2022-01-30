package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.security.token.PasswordResetToken;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken,Long> {
}
