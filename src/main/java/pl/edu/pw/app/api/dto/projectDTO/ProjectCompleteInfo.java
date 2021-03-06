package pl.edu.pw.app.api.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Getter
public class ProjectCompleteInfo {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 40)
    private String name;

    private String category;

    @Size(max = 200)
    private String description;

    @NotNull
    private List<ProjectMemberInfo> members;
}
