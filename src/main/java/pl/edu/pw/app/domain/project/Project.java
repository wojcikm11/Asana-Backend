package pl.edu.pw.app.domain.project;

import lombok.*;
import pl.edu.pw.app.domain.task.Task;
import pl.edu.pw.app.domain.team.Team;
import pl.edu.pw.app.domain.team.TeamMember;
import pl.edu.pw.app.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private String description;

    public Project(User user, String name, String category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
        ProjectMember projectMember = new ProjectMember(user, this, ProjectMember.Role.OWNER);
        this.members.add(projectMember);
    }

    public Project(Project project) {
        this.id = project.id;
        this.name = project.name;
        this.category = project.category;
        this.description = project.description;
        this.members = new ArrayList<>(project.getMembers());
        this.teams = new HashSet<>(project.getTeams());
    }

    @ManyToMany(mappedBy = "favoriteProjects")
    private Set<User> usersFavouritePosts = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    private Set<Team> teams = new HashSet<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProjectMember> members = new ArrayList<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages = new ArrayList<>();

    public ProjectMember getOwner() {
        return members.stream().filter(member -> member.getRole() == ProjectMember.Role.OWNER).findAny().orElse(null);
    }

    public ProjectMember getProjectMemberByUserId(Long userId) {
        return members.stream().filter(member -> member.getId().getMemberId().equals(userId)).findAny().orElse(null);
    }

    public void addProjectMember(User user) {
        if (user != null && !getProjectUsers().contains(user)) {
            System.out.println("Dodaje u??ytkownika o id " + user.getId());
            ProjectMember projectMember = new ProjectMember(user, this, ProjectMember.Role.MEMBER);
            members.add(projectMember);
            user.getProjects().add(projectMember);
        }
    }

    public void addProjectMember(ProjectMember projectMember) {
        if (projectMember != null && !members.contains(projectMember)) {
            members.add(projectMember);
        }
    }

    public void addOwner(ProjectMember projectOwner) {
        this.members.add(projectOwner);
        projectOwner.setProject(this);
    }

    private List<User> getProjectUsers() {
        return members.stream().map(ProjectMember::getUser).toList();
    }

    public void removeProjectMember(User user) {
        if (user != null) {
            ProjectMember projectMember = getProjectMemberByUserId(user.getId());
            members.remove(projectMember);
            user.getProjects().remove(projectMember);
        }
    }

    public void removeProjectMember(ProjectMember projectMember) {
        if (projectMember != null) {
            members.remove(projectMember);
            projectMember.getProject().getMembers().remove(projectMember);
        }
    }

    public List<TeamMember> getTeamMembersByTeamId(Long teamId) {
//        teams.stream().filter(team -> )
        return null;
    }

    public void addTask(Task task){
        if(task!=null){

        }
    }

    public void addTeam(Team team) {
        if (team != null) {
            if (!teams.contains(team)) {
                teams.add(team);
                team.getProjects().add(this);
            }
            for (TeamMember member : team.getMembers()) {
                addProjectMember(member.getUser());
            }
        }
    }

    public void detachTeam(Team team) {
        if (team != null) {
            teams.remove(team);
            team.getProjects().remove(this);
        }
    }

    public void removeTeam(Team team) {
        if (team != null) {
            teams.remove(team);
            team.getProjects().remove(this);
            for (TeamMember member : team.getMembers()) {
                removeProjectMember(member.getUser());
            }
        }
    }
}
