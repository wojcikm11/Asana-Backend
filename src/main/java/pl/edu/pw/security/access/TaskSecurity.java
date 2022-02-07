package pl.edu.pw.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.app.api.service.UtilityService;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.Task;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.TaskRepository;

@Component("taskSecurity")
public class TaskSecurity {

    private TaskRepository taskRepository;

    @Autowired
    public TaskSecurity(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean isProjectMember(Long taskId) {
        User user = UtilityService.getLoggedUser();
        Project project = taskRepository.findById(taskId).orElseThrow().getProject();
        return project.getProjectMemberByUserId(user.getId()) != null;
    }
}
