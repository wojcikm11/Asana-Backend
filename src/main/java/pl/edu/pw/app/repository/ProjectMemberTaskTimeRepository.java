package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.app.domain.project.ProjectMemberTaskTime;
import pl.edu.pw.app.domain.project.ProjectMemberTaskTimeId;
@Repository
public interface ProjectMemberTaskTimeRepository extends JpaRepository<ProjectMemberTaskTime, ProjectMemberTaskTimeId> {
}
