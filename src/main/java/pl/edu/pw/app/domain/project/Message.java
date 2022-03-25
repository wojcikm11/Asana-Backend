package pl.edu.pw.app.domain.project;

import lombok.*;
import pl.edu.pw.app.domain.project.Project;
import pl.edu.pw.app.domain.project.ProjectMember;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "project_id", insertable = false, updatable = false)
    })
    private ProjectMember projectMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private String message;

    private LocalDateTime date;
}
