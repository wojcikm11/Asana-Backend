package pl.edu.pw.app.api.dto.teamDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamCompleteInfo {
    private Long id;
    private String name;
    private List<TeamMemberBasicInfo> members;
}
