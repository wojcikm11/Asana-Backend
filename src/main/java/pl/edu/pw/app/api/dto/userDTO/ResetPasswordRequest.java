package pl.edu.pw.app.api.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pw.security.validator.FieldMatch;

@Data
@AllArgsConstructor
@FieldMatch.List(

        @FieldMatch(first = "password",
                second = "confirmPassword",
                message = "Passwords do not match!")
)
public class ResetPasswordRequest {

    private String token;
    private String password;
    private String confirmPassword;
}
