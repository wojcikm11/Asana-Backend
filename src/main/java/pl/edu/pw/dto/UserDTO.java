package pl.edu.pw.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Setter(AccessLevel.NONE)
public class UserDTO {

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
