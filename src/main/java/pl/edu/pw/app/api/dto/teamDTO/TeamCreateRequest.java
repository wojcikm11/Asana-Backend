package pl.edu.pw.app.api.dto.teamDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class TeamCreateRequest {
    @NotBlank
    private String name;

    private List<Long> members = new ArrayList<>();

}
