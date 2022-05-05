package pl.edu.pw.app.api.service.project;

import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;
import pl.edu.pw.app.api.dto.timeDTO.ProjectTasksTime;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface ProjectTimeService {
    void addTimeToTask(AddTaskTimeForProjectMember addTime);
    ProjectTasksTime getProjectTasksTime(Long projectId);
    List<ProjectTasksTime> getAllProjectTasksTime();
    List<ProjectTasksTime> getProjectAllTasksTime(Long projectId);
}
