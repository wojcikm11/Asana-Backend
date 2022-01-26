package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.app.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
