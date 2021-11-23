package pl.edu.pw.domain.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Setter(AccessLevel.PUBLIC)
public class UserUpdateRequest {

    @NotNull
    @Size(max=50)
    private String email;

    @NotNull
    @Size(max=40)
    private String password;

    @NotNull
    @Size(max=30)
    private String name;

    public UserUpdateRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
