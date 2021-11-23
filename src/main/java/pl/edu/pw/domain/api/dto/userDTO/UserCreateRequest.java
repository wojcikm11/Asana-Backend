package pl.edu.pw.domain.api.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UserCreateRequest {

    @NotNull
    @Size(max=50)
    private String email;

    @NotNull
    @Size(max=40)
    private String password;

    @NotNull
    @Size(max=30)
    private String name;


}
