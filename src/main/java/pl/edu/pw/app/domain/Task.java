package pl.edu.pw.app.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(
            mappedBy="task",
            cascade=CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Subtask> subtasks = new ArrayList<>();

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
    private Status status = Status.UNDONE;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "task_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = { @JoinColumn(name = "user_id"), @JoinColumn(name = "project_id") })
    private Set<ProjectMember> taskAssignees = new HashSet<>();

    public Task(Project project, String name, String description, LocalDateTime startDate, LocalDateTime deadLine, Priority priority) {
        this.project=project;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.deadLine = deadLine;
        this.priority = priority;
    }
}
