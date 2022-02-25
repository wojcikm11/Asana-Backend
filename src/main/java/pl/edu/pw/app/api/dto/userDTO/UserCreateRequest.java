package pl.edu.pw.app.api.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class UserCreateRequest {

    @NotNull
    @Email(
            message = "Invalid email",
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @NotNull
    @Size(max = 40)
    private String password;


    @NotNull
    @Size(max = 30)
    private String name;


}
