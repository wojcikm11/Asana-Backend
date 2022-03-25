package pl.edu.pw.app.api.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;
import pl.edu.pw.app.api.service.common.UtilityService;
import pl.edu.pw.app.domain.project.Project;
import pl.edu.pw.app.domain.project.ProjectMember;
import pl.edu.pw.app.domain.task.Task;
import pl.edu.pw.app.repository.TaskRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProjectTimeServiceImpl implements ProjectTimeService {
    private TaskRepository taskRepository;

    @Autowired
    public ProjectTimeServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTimeToTask(AddTaskTimeForProjectMember addTime) {
        Task task = taskRepository.findById(addTime.getTaskId()).orElseThrow();
        Project project = task.getProject();

        ProjectMember projectMember = project.getProjectMemberByUserId(UtilityService.getLoggedUser().getId());
        projectMember.addTimeForTask(task, addTime.getTimeToAdd());
    }
}
