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
import pl.edu.pw.app.domain.user.User;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TaskRepository;
import pl.edu.pw.app.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

@Service
@Transactional
public class ProjectTimeServiceImpl implements ProjectTimeService {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectTimeServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
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

        return findTimeOnProject(projectMember);
    }

    @Override
    public List<ProjectTasksTime> getProjectAllTasksTime(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        List<ProjectMember> members = project.getMembers();
        List<ProjectTasksTime> tasksTimes = new ArrayList<>();
        for(ProjectMember member : members) {
            ProjectTasksTime timeOnProject = findTimeOnProject(member);
            timeOnProject.setProjectName(member.getUser().getName());
            tasksTimes.add(timeOnProject);
        }
        return tasksTimes;
    }

    @Override
    public List<ProjectTasksTime> getAllProjectTasksTime() {
        User user = userRepository.getById(UtilityService.getLoggedUser().getId()); // musi tak byc, bo w samym getLoggedUser() nie ma informacji o relacjach z innymi tabelami, wiec trzeba pobraz z bazy
        List<ProjectMember> userProjects = user.getProjects();
        List<ProjectTasksTime> totalProjectsTime = new ArrayList<>();
        for (ProjectMember projectMember : userProjects) {
            ProjectTasksTime timeOnProject = findTimeOnProject(projectMember);
            if(timeOnProject.getTotalTimeOnProject() > 0) {
                totalProjectsTime.add(timeOnProject);
            }
        }
        return totalProjectsTime;
    }

    private ProjectTasksTime findTimeOnProject(ProjectMember projectMember) {
        List<TimeOnTask> timeOnTasks = projectMember.getTaskTimes().stream().map(ProjectTimeMapper::map).toList();
        int totalTimeSpentOnProject = projectMember.getTaskTimes().stream().
                map(ProjectMemberTaskTime::getTime).reduce(0, Integer::sum);

        return new ProjectTasksTime(projectMember.getProject().getId(), projectMember.getProject().getName(),
                totalTimeSpentOnProject, timeOnTasks);
    }

    public static class ProjectTimeMapper {
        public static TimeOnTask map(ProjectMemberTaskTime taskTime) {
            return new TimeOnTask(
                taskTime.getTask().getId(),
                taskTime.getTask().getName(),
                taskTime.getTask().getStatus().name(),
                taskTime.getTime()
            );
        }
    }
}
