package pl.edu.pw.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.app.api.service.UtilityService;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.SubtaskRepository;
import pl.edu.pw.app.repository.TaskRepository;

@Component("subtaskSecurity")
public class SubtaskSecurity {

    private SubtaskRepository subtaskRepository;

    @Autowired
    public SubtaskSecurity(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    public boolean isProjectMember(Long subtaskId) {
        User user = UtilityService.getLoggedUser();
        Project project = subtaskRepository.findById(subtaskId).orElseThrow().getTask().getProject();
        return project.getProjectMemberByUserId(user.getId()) != null;
    }

}
