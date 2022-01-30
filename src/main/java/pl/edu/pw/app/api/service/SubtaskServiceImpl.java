package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.ProjectMemberBasicInfo;
import pl.edu.pw.app.api.dto.subtaskDTO.*;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.domain.*;
import pl.edu.pw.app.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class SubtaskServiceImpl implements SubtaskService {

    private SubtaskRepository subtaskRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    private final String TASK_NOT_FOUND_EXCEPTION = "Task with the given id not found";
    private final String SUBTASK_NOT_FOUND_EXCEPTION = "Subtask with the given id not found";
    private final String USER_NOT_FOUND_EXCEPTION = "User with the given id not found";
    private final String PROJECT_NOT_FOUND_EXCEPTION = "Project with the given id not found";

    @Override
    public void addSubtask(SubtaskCreateRequest subtask) {
        subtaskRepository.save(map(subtask));
    }

    @Override
    public void deleteSubtask(Long id) {
        Subtask subtask = subtaskRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(TASK_NOT_FOUND_EXCEPTION));
        subtaskRepository.delete(subtask);
    }

    @Override
    public void updateSubtask(SubtaskUpdateRequest updatedSubtask, Long id) {
        Subtask subtask = subtaskRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(SUBTASK_NOT_FOUND_EXCEPTION));
        subtask.setName(updatedSubtask.getName());
        subtask.setDescription(updatedSubtask.getDescription());
        subtask.setStartDate(updatedSubtask.getStartDate());
        subtask.setDeadLine(updatedSubtask.getDeadLine());
        subtask.setStatus(updatedSubtask.getStatus());
        subtask.setPriority(updatedSubtask.getPriority());
        subtaskRepository.save(subtask);
    }

    @Override
    public List<SubtaskBasicInfo> getSubtasks(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(TASK_NOT_FOUND_EXCEPTION));
        return task.getSubtasks().stream().map(this::map).toList();
    }

    @Override
    public List<SubtaskCompleteInfo> getSubtasksDetails(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(TASK_NOT_FOUND_EXCEPTION));
        return task.getSubtasks().stream().map(this::mapDetails).toList();
    }

    @Override
    public void addAssignee(AssignRequest assign) {

        userRepository.findById(assign.getUserId()).orElseThrow(
                ()-> new IllegalArgumentException(USER_NOT_FOUND_EXCEPTION)
        );
        Subtask subtask = subtaskRepository.findById(assign.getSubtaskId()).orElseThrow(
                ()-> new IllegalArgumentException(SUBTASK_NOT_FOUND_EXCEPTION)
        );
        Project project = projectRepository.findById(assign.getProjectId()).orElseThrow(
                ()-> new IllegalArgumentException(PROJECT_NOT_FOUND_EXCEPTION)
        );

        subtask.getSubtaskAssignees().add(
                project.getProjectMemberByUserId(assign.getUserId())
        );
        subtaskRepository.save(subtask);


    }

    @Override
    public SubtaskCompleteInfo getSubtaskDetails(Long id) {
        Subtask subtask = subtaskRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException(SUBTASK_NOT_FOUND_EXCEPTION)
        );
        return mapDetails(subtask);
    }

    @Override
    public void removeAssignee(Long subtaskId, Long assigneeId) {
        Subtask subtask = subtaskRepository.findById(subtaskId).orElseThrow();
        Project project = subtask.getTask().getProject();
        subtask.removeAssignee(project.getProjectMemberByUserId(assigneeId));
        subtaskRepository.save(subtask);
    }

    private Subtask map(SubtaskCreateRequest subtask) {
        Task task = taskRepository.findById(subtask.getId()).orElseThrow(() ->
                new IllegalArgumentException(TASK_NOT_FOUND_EXCEPTION));

        return new Subtask(
                task,
                subtask.getName(),
                subtask.getDescription(),
                subtask.getStartDate(),
                subtask.getDeadLine(),
                subtask.getPriority()

        );
    }

    private SubtaskBasicInfo map(Subtask task){
        return new SubtaskBasicInfo(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getDeadLine()
        );
    }

    private SubtaskCompleteInfo mapDetails(Subtask task){
        return new SubtaskCompleteInfo(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStartDate(),
                task.getDeadLine(),
                task.getPriority(),
                task.getStatus(),
//                new ArrayList<ProjectMemberBasicInfo>()
                task.getSubtaskAssignees().stream().map(

                        a ->
                             new ProjectMemberBasicInfo(
                                    a.getUser().getId(),
                                    a.getUser().getName(),
                                    a.getUser().getEmail()
                            )

                ).toList()

                );



    }
}
