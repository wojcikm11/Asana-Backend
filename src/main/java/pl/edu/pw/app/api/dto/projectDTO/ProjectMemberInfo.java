package pl.edu.pw.app.api.dto.projectDTO;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectMemberInfo {
    @JsonUnwrapped
    private UserBasicInfo userBasicInfo;
    private String role;
}
