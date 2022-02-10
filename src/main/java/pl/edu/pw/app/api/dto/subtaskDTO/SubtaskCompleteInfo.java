package pl.edu.pw.app.api.dto.subtaskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.app.api.dto.projectDTO.ProjectMemberBasicInfo;
import pl.edu.pw.app.domain.Priority;
import pl.edu.pw.app.domain.Status;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Data
public class SubtaskCompleteInfo {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadLine;
    private Priority priority;
    private Status status;
    private List<ProjectMemberBasicInfo> assignees;
}
