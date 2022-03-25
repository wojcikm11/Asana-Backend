package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.app.domain.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

//    @Query("SELECT pm FROM ProjectMember pm, Project p, Team t, TeamMember tm WHERE pm.project.id = p.id AND tm.id.teamId = t.id AND NOT tm.id.memberId = pm.id.memberId")
//    @Transactional
//    List<ProjectMember> getProjectMembersNotInTeam(Long projectId);
}
