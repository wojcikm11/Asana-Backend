package pl.edu.pw.domain.model;

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
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private String description;

    @ManyToMany(mappedBy = "favoriteProjects")
    private Set<User> usersFavouritePosts = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    private Set<Team> teams = new HashSet<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProjectMember> members = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "project",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Task> tasks = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "project",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Message> messages = new ArrayList<>();
}
