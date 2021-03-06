package pl.edu.pw.app.api.service.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.taskDTO.*;
import pl.edu.pw.app.api.dto.timeDTO.SetTimeRequest;
import pl.edu.pw.app.api.service.common.UtilityService;
import pl.edu.pw.app.domain.project.Project;
import pl.edu.pw.app.domain.project.ProjectMember;
import pl.edu.pw.app.domain.task.Priority;
import pl.edu.pw.app.domain.task.Status;
import pl.edu.pw.app.domain.task.Task;
import pl.edu.pw.app.domain.user.User;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TaskRepository;
import pl.edu.pw.app.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.edu.pw.app.api.service.user.UserServiceImpl.UserMapper.mapToBasicInfo;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private static final String TASK_NOT_FOUND_EXCEPTION = "Task with given id not found";
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    private final String NO_PROJECT_FOUND = "Project with the given id not found";
    private final String NO_TASK_FOUND = "Task with the given id not found";
    private final String NO_PROJECT_MEMBER_FOUND = "Member with given id was not find in this project ";

    @Override
    public void addTask(TaskCreateRequest task) {
        Task newTask = map(task);
        taskRepository.save(newTask);
       if(task.getAssigneeId()!=null)
           newTask.addAssignee(projectRepository.getById(task.getProjectId()).getProjectMemberByUserId(task.getAssigneeId()));

    }

    @Override
    public List<TaskBasicInfo> getTasks(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException(NO_PROJECT_FOUND);
        });
        return project.getTasks().stream().map(this::map).toList();
    }

    //    todo naprawic
    @Override
    public List<TaskDetails> getTasksDetails(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException(NO_PROJECT_FOUND);
        });
        return project.getTasks().stream().map(this::mapTaskDetails).toList();
    }

    @Override
    public void addAssignee(AddAssigneeRequest addAssignee) {
        Task task = taskRepository.findById(addAssignee.getTaskId()).orElseThrow(() -> {
            throw new IllegalArgumentException(NO_TASK_FOUND);
        });
        Project project = task.getProject();
        ProjectMember taskAssignee = project.getProjectMemberByUserId(addAssignee.getUserId());
        task.addAssignee(taskAssignee);
        taskRepository.save(task);
    }

    @Override
    public void removeAssignee(Long taskId, Long assigneeId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        Project project = task.getProject();
        task.removeAssignee(project.getProjectMemberByUserId(assigneeId));
    }

    @Override
    public TaskDetails getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(NO_TASK_FOUND));
        return mapTaskDetails(task);
    }

    @Override
    public void addTime(Long id, TaskTimeAdd taskTimeAdd) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(NO_TASK_FOUND));
        log.warn(String.valueOf(taskTimeAdd.getTimeToAdd()));
        task.addTime(taskTimeAdd.getTimeToAdd());
    }

    @Override
    public void postponeDeadlines(Long projectId, PostponeDeadlinesRequest time) {
        Project project = projectRepository.getById(projectId);
        List<Task> tasks = project.getTasks();
        tasks.stream().forEach(t-> {
            t.setDeadLine(t.getDeadLine().plusSeconds(time.getTime()));
        });
    }

    @Override
    public void setTime(Long taskId, SetTimeRequest time) {
        Task task = taskRepository.getById(taskId);
        if(time.getTime()>=0){
          task.setTotalTime(Math.toIntExact(time.getTime()));
        }
    }

    @Override
    public List<TaskDetails> getUserAssignedTasks() {
        User user = userRepository.getById(UtilityService.getLoggedUser().getId());
        List<TaskDetails> userAssignedTasks = new ArrayList<>();
        for (ProjectMember projectMember : user.getProjects()) {
            Set<TaskDetails> assignedTasks = projectMember.getTasks().stream().map(this::mapTaskDetails).collect(Collectors.toSet());
            userAssignedTasks.addAll(assignedTasks);
        }
        return userAssignedTasks;
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException(NO_TASK_FOUND);
        });
        task.getProject().getTasks().remove(task);
        taskRepository.delete(task);
    }

    @Override
    public void updateTask(TaskUpdateRequest updatedTask, Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(TASK_NOT_FOUND_EXCEPTION));
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setStartDate(updatedTask.getStartDate());
        task.setDeadLine(updatedTask.getDeadLine());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        task.getTaskAssignees().stream().forEach(a->{
            task.removeAssignee(a);
        });
        if(updatedTask.getAssigneeId()!=null) {
            log.info("assignee: {}", (updatedTask.getAssigneeId()));
            task.addAssignee(task.getProject().getProjectMemberByUserId(updatedTask.getAssigneeId()));
        }
        taskRepository.save(task);
    }

    @Override
    public void updateTaskStatus(TaskStatusUpdateRequest taskStatus, Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(TASK_NOT_FOUND_EXCEPTION));
        Status taskUpdatedStatus = Status.valueOf(taskStatus.getUpdatedTask());
        task.setStatus(taskUpdatedStatus);
    }

    private Task map(TaskCreateRequest task) {
        return new Task(
                projectRepository.getById(task.getProjectId()),
                task.getName(),
                task.getDescription(),
                task.getStartDate(),
                task.getDeadLine(),
                Priority.valueOf(task.getPriority())
        );
    }

    private TaskBasicInfo map(Task task) {
        return new TaskBasicInfo(
                task.getName(),
                task.getDeadLine(),
                task.getStatus().toString()
        );
    }

    private TaskDetails mapTaskDetails(Task task) {

        return new TaskDetails(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStartDate(),
                task.getDeadLine(),
                task.getStatus().toString(),
                task.getPriority().toString(),
                task.getTotalTime(),
                task.getTaskAssignees().stream().map(m -> {
                    return mapToBasicInfo(m.getUser());
                }).collect(Collectors.toList())
        );
    }
}

