package pl.edu.pw.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY

    )
    private List<TeamMember> members = new ArrayList<>();


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "project_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects = new HashSet<>();




    public Team(String name, User user) {
        TeamMember teamMember = new TeamMember(user, this, TeamMember.Role.MEMBER);
        members.add(teamMember);
        this.name = name;
    }

    public Team(String name) {
        this.name = name;
    }

    public void addMember(User user) {
        TeamMember teamMember = new TeamMember(user, this, TeamMember.Role.MEMBER);
        members.add(teamMember);
        user.getTeams().add(teamMember);
    }

    public void addMember(User user, TeamMember.Role role) {
        TeamMember teamMember = new TeamMember(user, this, role);
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

    public boolean isOwner(String email) {
        TeamMember member =
                members
                .stream().filter(
                m-> m.getUser().getEmail().equals(email))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException(ErrorMessage.NOT_PROJECT_MEMBER_EXCEPTION));
        return member.getRole()== TeamMember.Role.OWNER;

    }

    private class ErrorMessage{
        private static final String NOT_PROJECT_MEMBER_EXCEPTION ="User with the given email is not a member of this project";
     }
}
