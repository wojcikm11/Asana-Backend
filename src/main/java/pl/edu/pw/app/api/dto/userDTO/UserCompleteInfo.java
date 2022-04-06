package pl.edu.pw.app.api.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.app.api.dto.projectDTO.ProjectBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCompleteInfo {

    private Long id;
    private String email;
    private String name;
    private List<TeamBasicInfo> teams;
    private List<ProjectBasicInfo> projects;

}
