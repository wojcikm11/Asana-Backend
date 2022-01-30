package pl.edu.pw.app.api.dto.taskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AddAssigneeRequest {

    @NotNull
    private Long taskId;

    @NotNull
    private Long userId;
}
