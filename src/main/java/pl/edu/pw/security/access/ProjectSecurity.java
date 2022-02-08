package pl.edu.pw.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.edu.pw.app.api.dto.projectDTO.AddProjectMember;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.service.ProjectService;
import pl.edu.pw.app.api.service.UtilityService;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.Team;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TeamRepository;

@Component("projectSecurity")
public class ProjectSecurity {

    private ProjectRepository projectRepository;
    private TeamRepository teamRepository;

    @Autowired
    public ProjectSecurity(ProjectRepository projectRepository, TeamRepository teamRepository) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
    }

    public boolean isProjectOwner(Long projectId) {
        User user = UtilityService.getLoggedUser();
        Project project = projectRepository.findById(projectId).orElseThrow();
        return project.getOwner().getUser().getId().equals(user.getId());
    }

    public boolean isProjectMember(Long projectId) {
        User user = UtilityService.getLoggedUser();
        Project project = projectRepository.findById(projectId).orElseThrow();
        return project.getProjectMemberByUserId(user.getId()) != null;
    }

    public boolean isTeamMember(Long teamId) {
        User user = UtilityService.getLoggedUser();
        Team team = teamRepository.findById(teamId).orElseThrow();
        return team.getTeamMemberByUserId(user.getId()) != null;
    }
}
