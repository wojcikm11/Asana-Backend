package pl.edu.pw.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
