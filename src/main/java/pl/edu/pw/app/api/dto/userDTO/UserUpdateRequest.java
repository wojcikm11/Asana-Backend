package pl.edu.pw.app.api.dto.userDTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {

    @Size(max=50)
    private String email;

    @NotNull
    @Size(max=40)
    private String password;

    @NotNull
    @Size(max=30)
    private String name;
}
