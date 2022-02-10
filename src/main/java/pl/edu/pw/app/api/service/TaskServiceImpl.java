package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.taskDTO.*;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;
import pl.edu.pw.app.domain.*;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private static final String TASK_NOT_FOUND_EXCEPTION = "Task with given id not found";
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;

    private final String NO_PROJECT_FOUND = "Project with the given id not found";
    private final String NO_TASK_FOUND = "Task with the given id not found";
    private final String NO_PROJECT_MEMBER_FOUND = "Member with given id was not find in this project ";

    @Override
    public void addTask(TaskCreateRequest task) {
        Task newTask = map(task);
        taskRepository.save(newTask);
//      TODO spr. czy projekt istnieje
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
        Task task = taskRepository.findById(addAssignee.getTaskId()).orElseThrow(()->{
            throw new IllegalArgumentException(NO_TASK_FOUND);
        });
        Project project = task.getProject();
//        project.getMembers().forEach(m-> {
//            if (!m.getId().getMemberId().equals(addAssignee.getUserId())) {
//                throw new RuntimeException(NO_PROJECT_MEMBER_FOUND);
//            }
//        });
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
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException(NO_TASK_FOUND);
        });
        task.getProject().getTasks().remove(task);
        taskRepository.delete(task);
    }

    @Override
    public void updateTask(TaskUpdateRequest updatedTask, Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(TASK_NOT_FOUND_EXCEPTION));
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setStartDate(updatedTask.getStartDate());
        task.setDeadLine(updatedTask.getDeadLine());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        taskRepository.save(task);
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
//        List<UserBasicInfo> assignees = new ArrayList<>();
//        for(ProjectMember p: task.getTaskAssignees() ){
//            System.out.println(p.getUser().getEmail());
//        }
//        task.getProjectMembers().stream().forEach(m->{
//            assignees.add(new UserBasicInfo(
//                    m.getUser().getId(),
//                    m.getUser().getName(),
//                    m.getUser().getEmail()
//            ));
//        });

        List<UserBasicInfo> assignees = new ArrayList<>();
        task.getTaskAssignees().forEach((taskAssignee) -> {
                    assignees.add(new UserBasicInfo(
                            taskAssignee.getUser().getId(),
                            taskAssignee.getUser().getName(),
                            taskAssignee.getUser().getEmail()
            ));
                }
        );

        return new TaskDetails(
                task.getName(),
                task.getDescription(),
                task.getStartDate(),
                task.getDeadLine(),
                task.getStatus().toString(),
                task.getPriority().toString(),
                assignees
        );
    }
}

