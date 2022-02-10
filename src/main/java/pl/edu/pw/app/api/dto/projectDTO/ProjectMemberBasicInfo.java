package pl.edu.pw.app.api.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProjectMemberBasicInfo {

    private Long id;
    private String name;
    private String email;

}
