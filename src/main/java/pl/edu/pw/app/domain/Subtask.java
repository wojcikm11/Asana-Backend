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
@EqualsAndHashCode(exclude = "subtaskAssignees")
public class Subtask {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @ManyToMany(
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "subtask_assignees",
            joinColumns = @JoinColumn(name = "subtask_id"),
            inverseJoinColumns = { @JoinColumn(name="user_id"), @JoinColumn(name = "project_id") })
    private Set<ProjectMember> subtaskAssignees = new HashSet<>();

    @Column
    private String name;
    @Column
    private String description;
    @Column
    private LocalDateTime startDate;

    @Column(name="deadline")
    private LocalDateTime deadLine;

    @Column
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status =Status.UNDONE;

    public Subtask(Task task, String name, String description, LocalDateTime startDate, LocalDateTime deadLine, Priority priority) {
        this.task = task;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.deadLine = deadLine;
        this.priority = priority;
    }

    public void removeAssignee(ProjectMember subtaskAssignee) {
        if (subtaskAssignee != null) {
            this.subtaskAssignees.remove(subtaskAssignee);
            subtaskAssignee.getSubtasks().remove(this);
        }
    }
}
