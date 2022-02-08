package pl.edu.pw.app.api.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddTeam {
    private Long teamId;
    private Long projectId;
}
