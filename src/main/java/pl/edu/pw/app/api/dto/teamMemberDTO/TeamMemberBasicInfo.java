package pl.edu.pw.app.api.dto.teamMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.pw.app.domain.team.TeamMember;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class TeamMemberBasicInfo {

    @NotNull
    private Long id;

    @NotNull
    @Size(max=30)
    private String name;

    @NotNull
    @Size(max=50)
    private String email;

    @NotNull
    private TeamMember.Role role;

}