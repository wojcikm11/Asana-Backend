package pl.edu.pw.app.api.dto.teamMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTeamMemberRequest {
    @NotNull
    private Long memberId;
    @NotNull
    private Long teamId;
}
