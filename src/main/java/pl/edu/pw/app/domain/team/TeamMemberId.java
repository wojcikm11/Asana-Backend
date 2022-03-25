package pl.edu.pw.app.domain.team;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class TeamMemberId implements Serializable {

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "user_id")
    private Long memberId;
}
