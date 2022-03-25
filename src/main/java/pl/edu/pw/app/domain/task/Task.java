package pl.edu.pw.app.domain.task;

import lombok.*;
import pl.edu.pw.app.domain.project.Project;
import pl.edu.pw.app.domain.project.ProjectMember;
import pl.edu.pw.app.domain.project.ProjectMemberTaskTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "taskAssignees")
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

    @Column
    private int totalTime;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "task_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = { @JoinColumn(name = "user_id"), @JoinColumn(name = "project_id") })
    private Set<ProjectMember> taskAssignees = new HashSet<>();

    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProjectMemberTaskTime> projectMemberTaskTimes = new ArrayList<>();

    public Task(Project project, String name, String description, LocalDateTime startDate, LocalDateTime deadLine, Priority priority) {
        this.project = project;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.deadLine = deadLine;
        this.priority = priority;
        this.totalTime = 0;
    }

    public void addAssignee(ProjectMember taskAssignee) {
        if (taskAssignee != null) {
            this.taskAssignees.add(taskAssignee);
            taskAssignee.getTasks().add(this);
        }
    }

    public void removeAssignee(ProjectMember taskAssignee) {
        if (taskAssignee != null) {
            this.taskAssignees.remove(taskAssignee);
            taskAssignee.getTasks().remove(this);
        }
    }

    public void addTime(int timeToAdd) {
        if (timeToAdd > 0) {
            setTotalTime(totalTime + timeToAdd);
        }
    }
}
