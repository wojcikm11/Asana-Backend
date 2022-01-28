package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.taskDTO.AddAssigneeRequest;
import pl.edu.pw.app.api.dto.taskDTO.TaskBasicInfo;
import pl.edu.pw.app.api.dto.taskDTO.TaskCreateRequest;
import pl.edu.pw.app.api.dto.taskDTO.TaskDetails;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;
import pl.edu.pw.app.domain.Priority;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.ProjectMember;
import pl.edu.pw.app.domain.Task;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {


    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;


    private final String NO_PROJECT_FOUND = "Project with the given id not found";
    private final String NO_TASK_FOUND = "Task with the given id not found";
    private final String NO_PROJECT_MEMBER_FOUND = "Member with given id was not find in this project ";

    @Override
    public void addTask(TaskCreateRequest task) {
        Task newTask = map(task);
        taskRepository.save(newTask);
//        todo spr. czy projekt istnieje



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
    public void addAssignee(AddAssigneeRequest assignee) {
        Project project = projectRepository.findById(assignee.getProjectId()).orElseThrow(()->{
            throw new IllegalArgumentException(NO_PROJECT_FOUND);
        });

        project.getMembers().stream().forEach(m->{
            if(m.getId().getMemberId()==assignee.getUserId()) {
                return;
            }
            throw new RuntimeException(NO_PROJECT_MEMBER_FOUND);
        });

        Task task = taskRepository.findById(assignee.getTaskId()).orElseThrow(()->{
            throw new IllegalArgumentException(NO_TASK_FOUND);
        });
        project.addTask(task);

    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException(NO_TASK_FOUND);
        });
        task.getProject().getTasks().remove(task);
        taskRepository.delete(task);
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

