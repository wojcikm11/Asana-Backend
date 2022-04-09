package pl.edu.pw.app.api.dto.taskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskCreateRequest {

    @NotNull
    private Long projectId;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime deadLine;

    @NotNull
    private String priority;


    private Long assigneeId;

}
