package pl.edu.pw.app.api.dto.taskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskDetails {

    @NotNull
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadLine;
    private String status;
    private String priority;
    private List<UserBasicInfo> assignees;
}
