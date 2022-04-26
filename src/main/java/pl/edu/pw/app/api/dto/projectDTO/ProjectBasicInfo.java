package pl.edu.pw.app.api.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
public class ProjectBasicInfo {

    @NotNull
    private int id;

    @NotNull
    @Size(max=40)
    private String name;

    private String description;

    @NotNull
    private boolean isOwner;

    private Long teamId;

    private String teamName;
}
