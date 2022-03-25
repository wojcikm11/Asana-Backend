package pl.edu.pw.app.api.service.project;

import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;
import pl.edu.pw.app.api.dto.timeDTO.ProjectTasksTime;

public interface ProjectTimeService {
    void addTimeToTask(AddTaskTimeForProjectMember addTime);
    ProjectTasksTime getProjectTasksTime(Long projectId);
}
