package pl.edu.pw.app.api.dto.timeDTO;

import lombok.*;

@Data
@AllArgsConstructor
public class TimeOnTask {
    private Long taskId;
    private String taskName;
    private int timeSpent;
}
