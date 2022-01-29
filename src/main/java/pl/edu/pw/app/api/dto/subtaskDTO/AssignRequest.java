package pl.edu.pw.app.api.dto.subtaskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AssignRequest {

    private Long userId;
    private Long subtaskId;
    private Long projectId;
}
