package pl.edu.pw.app.api.service;

import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.*;
import pl.edu.pw.app.api.dto.teamDTO.TeamCompleteInfo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public interface IProjectService {
    void create(ProjectCreateRequest project);
    ProjectCompleteInfo get(Long id);
    List<ProjectCompleteInfo> getUserProjects(Long userId);
    void update(Long projectId, ProjectUpdateRequest project);
    void delete(Long id);
    void addUserToProject(AddProjectMember addProjectMember);
    void removeProjectMember(Long projectId, Long userId);
    void addTeam(AddTeam addTeam);
    void removeTeam(RemoveTeamFromProject removeTeam);
    Set<TeamCompleteInfo> getProjectTeamMembers(Long projectId);
}
