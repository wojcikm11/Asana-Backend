package pl.edu.pw.app.api.service;

import pl.edu.pw.app.api.dto.taskDTO.TaskBasicInfo;
import pl.edu.pw.app.api.dto.taskDTO.TaskCreateRequest;
import pl.edu.pw.app.api.dto.taskDTO.TaskDetails;

import java.util.List;

public interface TaskService {

    void addTask (TaskCreateRequest task);
    List<TaskBasicInfo> getTasks(Long id);
    List<TaskDetails> getTasksDetails(Long id);
}
