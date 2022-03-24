package pl.edu.pw.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "project_member_task_time")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProjectMemberTaskTime {
    @EmbeddedId
    private ProjectMemberTaskTimeId id = new ProjectMemberTaskTimeId();

    @Column
    private int time;

    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("projectMemberId")
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "project_id", referencedColumnName = "project_id", insertable = false, updatable = false),
    })

    private ProjectMember projectMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("taskId")
    private Task task;

    public void addTime(int timeToAdd) {
        if (timeToAdd > 0) {
            time += timeToAdd;
        }
    }
}
