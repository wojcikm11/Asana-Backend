package pl.edu.pw.app.api.dto.teamDTO;

import lombok.*;
import pl.edu.pw.app.domain.TeamMember;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamBasicInfo {
    private Long id;
    private String name;
    private List<TeamMember> members;
}
