package pl.edu.pw.app.api.dto.subtaskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.app.domain.Priority;
import pl.edu.pw.app.domain.Status;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class SubtaskCreateRequest {

//    task id
    @NotNull
    private Long id;

    @NotNull
    private String name;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime deadLine;

    private Priority priority;

    private Status status;
}
