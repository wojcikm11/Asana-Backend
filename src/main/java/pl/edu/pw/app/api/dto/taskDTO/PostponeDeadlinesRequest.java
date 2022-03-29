package pl.edu.pw.app.api.dto.taskDTO;

import com.sun.istack.NotNull;
import com.zaxxer.hikari.util.ClockSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostponeDeadlinesRequest {

    @NotNull
    private Long time;
}
