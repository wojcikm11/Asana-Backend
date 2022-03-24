package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import pl.edu.pw.app.domain.ProjectMemberTaskTime;
import pl.edu.pw.app.domain.ProjectMemberTaskTimeId;
@Repository
public interface ProjectMemberTaskTimeRepository extends JpaRepository<ProjectMemberTaskTime, ProjectMemberTaskTimeId> {
}
