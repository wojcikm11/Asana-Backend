package pl.edu.pw.app.api.service;

import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.*;
import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;
import pl.edu.pw.app.api.dto.teamDTO.TeamCompleteInfo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public interface IProjectService {
    ProjectCreateRequest create(ProjectCreateRequest project);
    ProjectCompleteInfo get(Long id);
    List<ProjectBasicInfo> getUserProjects(Long userId);
    List<ProjectCompleteInfo> getOwnerProjects(Long ownerId);
    void update(Long projectId, ProjectUpdateRequest project);
    void delete(Long id);
    void addUserToProject(AddProjectMember addProjectMember);
    void removeProjectMember(Long projectId, Long userId);
    void addTeam(AddTeam addTeam);
    void removeTeam(RemoveTeamFromProject removeTeam);
    Set<TeamCompleteInfo> getProjectTeamMembers(Long projectId);
    Set<ProjectMemberInfo> getProjectNonTeamMembers(Long projectId);
    void addTimeToTask(AddTaskTimeForProjectMember addTime);
}
