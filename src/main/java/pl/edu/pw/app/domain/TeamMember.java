package pl.edu.pw.app.domain;

import lombok.*;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TeamMember {

    @EmbeddedId
    private TeamMemberId id = new TeamMemberId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    private Team team;

//    @Enumerated(EnumType.STRING)
//    @Column(name="role")
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

    public enum Role {
        OWNER, MEMBER;
    }
}
