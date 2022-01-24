package pl.edu.pw.app.api.dto.projectDTO;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
public class ProjectUpdateRequest {

    @Size(max=40)
    private String name;

    private String category;

    @Size(max=200)
    private String description;

    @NotNull
    private String owner;
}
