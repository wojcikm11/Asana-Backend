package pl.edu.pw.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.edu.pw.app.api.dto.projectDTO.AddProjectMember;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.service.ProjectService;
import pl.edu.pw.app.api.service.UtilityService;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.ProjectRepository;

@Component("projectSecurity")
public class ProjectSecurity {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectSecurity(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public boolean isProjectOwner(Long projectId) {
        User user = UtilityService.getLoggedUser();
        Project project = projectRepository.findById(projectId).orElseThrow();
        return project.getOwner().getUser().getId().equals(user.getId());
    }
}
