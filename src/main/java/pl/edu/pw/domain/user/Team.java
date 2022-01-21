package pl.edu.pw.domain.user;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Team {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TeamMember> members;

    public void addMember(User user) {
        TeamMember teamMember = new TeamMember(user, this, TeamMember.Role.MEMBER);
        members.add(teamMember);
        user.getTeams().add(teamMember);
    }

    public void removeMember(User user) {
        TeamMember teamMember = new TeamMember(user, this, TeamMember.Role.MEMBER);
        members.remove(teamMember);
        user.getTeams().remove(teamMember);
        teamMember.setTeam(null);
        teamMember.setUser(null);
    }
}
