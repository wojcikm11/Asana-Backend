package pl.edu.pw.app.api.dto.userDTO;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UserBasicInfo {

    @NotNull
    private int id;

    @NotNull
    @Size(max=30)
    private String name;

    @NotNull
    @Size(max=50)
    private String email;

}
