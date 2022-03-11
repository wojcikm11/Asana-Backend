package pl.edu.pw.app.api.dto.teamDTO;

import lombok.*;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamCompleteInfo {
    private Long id;
    private String name;
    private List<TeamMemberBasicInfo> members;

}
