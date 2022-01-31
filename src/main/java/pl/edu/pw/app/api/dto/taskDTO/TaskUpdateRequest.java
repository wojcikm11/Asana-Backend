package pl.edu.pw.app.api.dto.taskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.app.domain.Priority;
import pl.edu.pw.app.domain.Status;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskUpdateRequest {
    @NotNull
    private String name;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime deadLine;

    private Priority priority;

    private Status status;
}
