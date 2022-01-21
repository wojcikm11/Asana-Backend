package pl.edu.pw.domain.user;

import lombok.*;

import javax.persistence.*;
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
    private int id;

    private String name;

    private String category;

    private String description;

    @ManyToMany(mappedBy = "project")
    private Set<User> usersFavouritePosts = new HashSet<>();
}
