package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.taskDTO.TaskCreateRequest;
import pl.edu.pw.app.common.UserUtils;
import pl.edu.pw.app.domain.Priority;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.Task;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TaskRepository;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {


    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;

    @Override
    public void addTask(TaskCreateRequest task) {
        Task newTask = map(task);
        taskRepository.save(newTask);
//        todo spr. czy projekt istnieje
//        Project project = projectRepository.getById(task.getProjectId());
//        project.getTasks().add(newTask);
//        Long id = UserUtils.getLoggedUser().getId();
//        project.getTasks().add(newTask);
//       project.getMembers().stream().forEach((m)->{
//           if(m.getUser().getId()==id){
//               m.getTasks().add(newTask);
//               return;
//           }
//       });


    }

    private Task  map(TaskCreateRequest task){
        return new Task(
                projectRepository.getById(task.getProjectId()),
                task.getName(),
                task.getDescription(),
                task.getStartDate(),
                task.getDeadLine(),
                Priority.valueOf(task.getPriority())
        );
    }
}
