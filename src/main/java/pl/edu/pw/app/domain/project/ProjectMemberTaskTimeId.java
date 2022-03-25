package pl.edu.pw.app.domain.project;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class ProjectMemberTaskTimeId implements Serializable {
    @Embedded
    private ProjectMemberId projectMemberId = new ProjectMemberId();

    @Column(name = "task_id")
    private Long taskId;
}
