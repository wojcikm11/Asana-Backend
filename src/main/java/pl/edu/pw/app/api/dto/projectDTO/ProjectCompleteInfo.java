package pl.edu.pw.app.api.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class ProjectCompleteInfo {

    @NotNull
    private int id;

    @NotNull
    @Size(max = 40)
    private String name;

    private String category;

    @Size(max = 200)
    private String description;

    @NotNull
    private String owner;
}
