package pl.edu.pw.app.api.dto.teamDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.app.domain.TeamMember;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamBasicInfo {

    private Long id;
    private String name;
    private List<TeamMember> members;


}
