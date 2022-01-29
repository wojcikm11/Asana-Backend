package pl.edu.pw.app.api.dto.subtaskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class SubtaskBasicInfo {

    private Long id;
    private String name;

    private String description;

    private LocalDateTime deadline;
}
