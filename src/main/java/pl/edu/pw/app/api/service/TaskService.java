package pl.edu.pw.app.api.service;

import pl.edu.pw.app.api.dto.taskDTO.TaskCreateRequest;

public interface TaskService {

    void addTask (TaskCreateRequest task);
}
