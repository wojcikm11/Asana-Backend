package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.app.domain.team.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
