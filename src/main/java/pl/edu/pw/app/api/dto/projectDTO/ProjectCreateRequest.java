package pl.edu.pw.app.api.dto.projectDTO;

import lombok.*;
import pl.edu.pw.app.api.dto.teamDTO.AddTeamToProject;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddMember;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateRequest {

    private Long id;

    @NotNull
    @Size(max=40)
    private String name;

    private String category;

    @Size(max=200)
    private String description;

    public ProjectCreateRequest(String name, String category) {
        this.name = name;
        this.category = category;
    }

    private List<AddTeamToProject> projectTeamsToAdd;

    private List<AddMember> membersToAdd;

    public ProjectCreateRequest(String name) {
        this.name=name;
    }

}
