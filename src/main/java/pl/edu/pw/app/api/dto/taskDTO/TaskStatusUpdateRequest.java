package pl.edu.pw.app.api.dto.taskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusUpdateRequest {

    @NotBlank
    private String updatedTask;

    public String getUpdatedTask() {
        return updatedTask.toUpperCase();
    }
}
