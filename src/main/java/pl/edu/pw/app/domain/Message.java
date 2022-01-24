//package pl.edu.pw.app.domain;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode
//public class Message {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long id;
////
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "user_id")
////    private ProjectMember projectMember;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "project_id")
//    private Project project;
//
//    private String message;
//
//    private LocalDateTime date;
//}
