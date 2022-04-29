package pl.edu.pw.app.api.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.app.api.dto.teamDTO.AddTeamToProject;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddMember;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProjectUpdateRequest {

    @Size(max=40)
    private String name;

    private String category;

    @Size(max=200)
    private String description;

    private List<AddTeamToProject> projectTeamsToAdd;

    private List<AddMember> membersToAdd;
}
