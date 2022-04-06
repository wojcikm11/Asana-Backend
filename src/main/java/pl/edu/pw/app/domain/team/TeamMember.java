package pl.edu.pw.app.domain.team;

import lombok.*;
import pl.edu.pw.app.domain.user.User;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TeamMember {

    @EmbeddedId
    private TeamMemberId id = new TeamMemberId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    private Team team;

    @Enumerated(EnumType.STRING)
    private Role role;

    public TeamMember(User user, Team team, Role role) {
        this.user = user;
        this.team = team;
        this.role = role;
    }

    public TeamMember(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public TeamMember(User user, Team team) {
        this.user = user;
        this.team = team;
    }

    public enum Role {
        OWNER, MEMBER;
    }
}
