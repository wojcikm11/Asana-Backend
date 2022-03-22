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
//@SecondaryTable(name = "project_member_task_time", pkJoinColumns =  {
//        @PrimaryKeyJoinColumn(name = "task_id"),
//        @PrimaryKeyJoinColumn(name = "user_id"),
//        @PrimaryKeyJoinColumn(name = "project_id")
//})
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

    @ManyToMany(mappedBy = "taskAssignees")
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(
            mappedBy = "projectMember",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProjectMemberTaskTime> taskTimes = new ArrayList<>();

    @ManyToMany(mappedBy = "subtaskAssignees")
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

    public ProjectMemberTaskTime getTaskTime(Long taskId) {
        return taskTimes.stream().
                filter(taskTime -> taskTime.getId().getTaskId().equals(taskId)).findAny().orElse(null);
    }

    public void addTimeForTask(Task task, int timeToAdd) {
        if (timeToAdd > 0) {
            ProjectMemberTaskTime projectMemberTaskTime = getTaskTime(task.getId());
            if (projectMemberTaskTime == null) {
                ProjectMemberTaskTimeId taskTimeId = new ProjectMemberTaskTimeId(new ProjectMemberId(project.getId(), id.getMemberId()), task.getId());
                ProjectMemberTaskTime newProjectMemberTaskTime = new ProjectMemberTaskTime(taskTimeId, timeToAdd,this, task);
                projectMemberTaskTime = newProjectMemberTaskTime;
            }
            projectMemberTaskTime.addTime(timeToAdd);
        }
    }
}
