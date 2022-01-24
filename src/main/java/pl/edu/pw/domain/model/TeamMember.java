package pl.edu.pw.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TeamMember {

    @EmbeddedId
    private TeamMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    private Team team;

    private Role role;

    public TeamMember(User user, Team team, Role role) {
        this.user = user;
        this.team = team;
        this.role = role;
    }

    public enum Role {
        OWNER, MEMBER;
    }
}
