package pl.edu.pw.app.api.dto.teamMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class DeleteTeamMemberRequest {

    @NotNull
    private Long memberId;
    @NotNull
    private Long teamId;
}
