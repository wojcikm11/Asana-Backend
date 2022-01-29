package pl.edu.pw.app.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Subtask {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "subtask_assignees",
            joinColumns = @JoinColumn(name = "subtask_id"),
            inverseJoinColumns = { @JoinColumn(name="user_id"), @JoinColumn(name = "project_id") })
    private Set<ProjectMember> subtaskAssignees = new HashSet<>();

    private String name;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime deadLine;

    private Priority priority;

    private Status status;
}
