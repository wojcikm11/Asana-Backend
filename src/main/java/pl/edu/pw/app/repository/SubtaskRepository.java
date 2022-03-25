package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.app.domain.task.Subtask;

public interface SubtaskRepository extends JpaRepository<Subtask,Long> {
}
