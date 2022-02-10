package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.TeamMember;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


}
