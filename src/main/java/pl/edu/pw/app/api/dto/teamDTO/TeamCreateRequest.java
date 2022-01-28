package pl.edu.pw.app.api.dto.teamDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamCreateRequest {
    @NotBlank
    private String name;
}
