package pl.edu.pw.app.api.dto.timeDTO;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectTasksTime {
    private Long projectId;
    private String projectName;
    private int totalTimeOnProject;
    private List<TimeOnTask> timeOnTaskList;
}
