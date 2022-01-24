package pl.edu.pw.domain.api.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class ProjectBasicInfo {

    @NotNull
    private int id;

    @NotNull
    @Size(max=40)
    private String name;

    @NotNull
    private String owner;


}
