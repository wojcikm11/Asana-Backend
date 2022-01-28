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
public class ProjectMember {
    @EmbeddedId
    private ProjectMemberId id = new ProjectMemberId();

    public ProjectMember(User user, Project project, Role role) {
        this.user = user;
        this.project = project;
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "projectMembers",fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(mappedBy = "projectMembers",fetch = FetchType.LAZY)
    private Set<Subtask> subtasks = new HashSet<>();

    @OneToMany(
            mappedBy = "projectMember",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages = new ArrayList<>();

    public enum Role {
        OWNER, MEMBER;
    }
}
