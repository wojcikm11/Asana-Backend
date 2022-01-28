package pl.edu.pw.app.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class ProjectMemberId implements Serializable {

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "user_id")
    private Long memberId;
}
