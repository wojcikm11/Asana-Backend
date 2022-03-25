package pl.edu.pw.app.api.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;
import pl.edu.pw.app.api.dto.timeDTO.ProjectTasksTime;
import pl.edu.pw.app.api.dto.timeDTO.TimeOnTask;
import pl.edu.pw.app.api.service.common.UtilityService;
import pl.edu.pw.app.domain.project.Project;
import pl.edu.pw.app.domain.project.ProjectMember;
import pl.edu.pw.app.domain.project.ProjectMemberTaskTime;
import pl.edu.pw.app.domain.task.Task;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjectTimeServiceImpl implements ProjectTimeService {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectTimeServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void addTimeToTask(AddTaskTimeForProjectMember addTime) {
        Task task = taskRepository.findById(addTime.getTaskId()).orElseThrow();
        Project project = task.getProject();

        ProjectMember projectMember = project.getProjectMemberByUserId(UtilityService.getLoggedUser().getId());
        projectMember.addTimeForTask(task, addTime.getTimeToAdd());
    }

    @Override
    public ProjectTasksTime getProjectTasksTime(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        ProjectMember projectMember = project.getProjectMemberByUserId(UtilityService.getLoggedUser().getId());
        List<TimeOnTask> timeOnTasks = projectMember.getTaskTimes().stream().map(ProjectTimeMapper::map).toList();
        int totalTimeSpentOnProject = projectMember.getTaskTimes().stream().
                map(ProjectMemberTaskTime::getTime).reduce(0, Integer::sum);

        return new ProjectTasksTime(project.getId(), project.getName(), totalTimeSpentOnProject, timeOnTasks);
    }

    public static class ProjectTimeMapper {
        public static TimeOnTask map(ProjectMemberTaskTime taskTime) {
            return new TimeOnTask(
                taskTime.getTask().getId(),
                taskTime.getTask().getName(),
                taskTime.getTime()
            );
        }
    }
}
