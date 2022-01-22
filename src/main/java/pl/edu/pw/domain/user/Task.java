package pl.edu.pw.domain.user;

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

    private String name;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime deadLine;

    private Priority priority;

    private Status status;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "task_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<ProjectMember> projectMembers = new HashSet<>();

    public enum Priority {
        LOW, MEDIUM, HIGH, VERY_HIGH
    }

    public enum Status {
        UNDONE, DOING, DONE
    }
}
