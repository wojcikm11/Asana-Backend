package pl.edu.pw.app.api.dto.taskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.app.domain.Priority;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class TaskBasicInfo {

    @NotNull
    private String name;
    private LocalDateTime deadLine;
    private String status;
}





