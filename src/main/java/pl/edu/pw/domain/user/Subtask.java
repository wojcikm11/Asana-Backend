package pl.edu.pw.domain.user;

import lombok.*;

import javax.persistence.*;
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
            inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<ProjectMember> projectMembers = new HashSet<>();
}
