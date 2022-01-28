package pl.edu.pw.app.domain;

import lombok.*;

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

    @ManyToMany(mappedBy = "favoriteProjects")
    private Set<User> usersFavouritePosts = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    private Set<Team> teams = new HashSet<>();

    @OneToMany(
            mappedBy = "project",
            cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE },
            orphanRemoval = true
    )
    private List<ProjectMember> members = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "project",
            cascade = CascadeType.ALL
    )
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages = new ArrayList<>();

    public ProjectMember getOwnerProjectMember() {
        return members.stream().filter(member -> member.getRole() == ProjectMember.Role.OWNER).findAny().orElse(null);
    }

    public ProjectMember getProjectMemberByUserId(Long userId) {
        return members.stream().filter(member -> member.getId().getMemberId().equals(userId)).findAny().orElse(null);
    }

    public void addTeamMember(User user) {
        if (user != null) {
            ProjectMember projectMember = new ProjectMember(user, this, ProjectMember.Role.MEMBER);
            members.add(projectMember);
            user.getProjects().add(projectMember);
        }
    }

    public void removeTeamMember(User user) {
        if (user != null) {
            ProjectMember projectMember = getProjectMemberByUserId(user.getId());
            members.remove(projectMember);
            user.getProjects().remove(projectMember);
        }
    }

    public void addTask(Task task){
        if(task!=null){

        }
    }
}
