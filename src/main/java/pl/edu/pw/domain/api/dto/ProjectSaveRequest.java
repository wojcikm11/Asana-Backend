package pl.edu.pw.domain.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class ProjectSaveRequest {

    @NotNull
    @Size(max=40)
    private String name;

    private String category;

    @Size(max=200)
    private String description;

    public ProjectSaveRequest(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public ProjectSaveRequest(String name) {
        this.name=name;
    }

}
