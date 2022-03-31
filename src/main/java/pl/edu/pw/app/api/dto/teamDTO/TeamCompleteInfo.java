package pl.edu.pw.app.api.dto.teamDTO;

import lombok.*;
import pl.edu.pw.app.api.dto.projectDTO.ProjectBasicInfo;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamCompleteInfo {
    private Long id;
    private String name;
    private List<TeamMemberBasicInfo> members;
    private List<ProjectBasicInfo> projects;

    public TeamCompleteInfo(Long id, String name, List<TeamMemberBasicInfo> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }
}
