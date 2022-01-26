package pl.edu.pw.app.api.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddProjectMember {
    @NotNull
    private Long projectId;

    @NotNull
    private Long userId;
}
