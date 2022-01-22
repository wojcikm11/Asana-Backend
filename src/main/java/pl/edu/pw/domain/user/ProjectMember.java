package pl.edu.pw.domain.user;

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
    private ProjectMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    private Project project;

    private String role;

    @ManyToMany(mappedBy = "projectMembers")
    private Set<Task> task = new HashSet<>();

    @ManyToMany(mappedBy = "projectMembers")
    private Set<Subtask> subtasks = new HashSet<>();

    @OneToMany(
            mappedBy = "projectMember",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages = new ArrayList<>();
}
