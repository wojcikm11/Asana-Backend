package pl.edu.pw.domain.api.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.pw.domain.model.Project;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserProjectsList {

    @NotNull
    private List<Project> projectsList;

}
