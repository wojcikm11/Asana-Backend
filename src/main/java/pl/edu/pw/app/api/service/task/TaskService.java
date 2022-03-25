package pl.edu.pw.app.api.service.task;

import pl.edu.pw.app.api.dto.taskDTO.*;

import java.util.List;

public interface TaskService {
    void addTask (TaskCreateRequest task);
    List<TaskBasicInfo> getTasks(Long id);
    List<TaskDetails> getTasksDetails(Long id);
    void deleteTask(Long id);
    void updateTask(TaskUpdateRequest updatedTask, Long id);
    void updateTaskStatus(TaskStatusUpdateRequest taskStatus, Long id);
    void addAssignee(AddAssigneeRequest assignee);
    void removeAssignee(Long taskId, Long assigneeId);
    TaskDetails getTaskById(Long id);
    void addTime(Long id, TaskTimeAdd taskTimeAdd);
}