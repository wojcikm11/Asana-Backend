package pl.edu.pw.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.app.api.dto.teamDTO.AddTeamToProject;
import pl.edu.pw.app.api.service.common.UtilityService;
import pl.edu.pw.app.domain.project.Project;
import pl.edu.pw.app.domain.team.Team;
import pl.edu.pw.app.domain.user.User;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TeamRepository;

import java.util.List;

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

    public boolean isTeamMember(List<AddTeamToProject> teamsToAddToProject) {
        User user = UtilityService.getLoggedUser();
        for (AddTeamToProject addTeamToProject : teamsToAddToProject) {
            Team team = teamRepository.findById(addTeamToProject.getTeamId()).orElseThrow();
            if (team.getTeamMemberByUserId(user.getId()) == null) {
                return false;
            }
        }
        return true;
    }
}
