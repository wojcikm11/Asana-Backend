package pl.edu.pw.app.api.dto.taskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTimeAdd {
    @NotNull
    private int timeToAdd;
}
