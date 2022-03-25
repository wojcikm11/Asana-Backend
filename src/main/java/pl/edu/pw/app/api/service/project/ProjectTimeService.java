package pl.edu.pw.app.api.service.project;

import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;

public interface ProjectTimeService {
    void addTimeToTask(AddTaskTimeForProjectMember addTime);
}
