package pl.edu.pw.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.app.api.service.UtilityService;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.Team;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.UserRepository;

@Component("userSecurity")
public class UserSecurity {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public UserSecurity(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public boolean isInUserFavorites(Long favoriteProjectId) {
        User user = UtilityService.getLoggedUser();
        Project project = projectRepository.findById(favoriteProjectId).orElseThrow();
        return user.inUserFavorites(project);
    }
}
