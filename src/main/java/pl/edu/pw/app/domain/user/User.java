package pl.edu.pw.app.domain.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pw.app.domain.project.Project;
import pl.edu.pw.app.domain.project.ProjectMember;
import pl.edu.pw.app.domain.team.Team;
import pl.edu.pw.app.domain.team.TeamMember;
import pl.edu.pw.security.config.PasswordSecurity;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Entity
@Table(name="user")
@Setter
@Getter
@EqualsAndHashCode(exclude = "favoriteProjects")
public class User implements PasswordSecurity, UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=50, unique = true)
    private String email;

    @Column(nullable = false, length=40)
    private String password;

    @Column(nullable = false, length=30)
    private String name;

    @Column
    private boolean locked;

    @Column
    private boolean enabled;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TeamMember> teams = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name="favorites",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="project_id")
    )
    private Set<Project> favoriteProjects = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProjectMember> projects = new ArrayList<>();

    @Override
    public String encrypt(String password) {
        return null;
    }

    public User(String email, String password, String name) {
        this.email=email;
        this.password=password;
        this.name=name;
        this.locked = false;
        this.enabled = false;
    }

    public boolean inUserFavorites(Project project) {
        return favoriteProjects.contains(project);
    }

    public void addTeam(Team team) {
        TeamMember teamMember = new TeamMember(this, team, TeamMember.Role.MEMBER);
        teams.add(teamMember);
        team.getMembers().add(teamMember);
    }

    public void addToFavorites(Project favoriteProject) {
        if (favoriteProject != null) {
            favoriteProjects.add(favoriteProject);
            favoriteProject.getUsersFavouritePosts().add(this);
        }
    }

    public void removeFromFavorites(Project favoriteProject) {
        if (favoriteProject != null) {
            favoriteProjects.remove(favoriteProject);
            favoriteProject.getUsersFavouritePosts().remove(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
