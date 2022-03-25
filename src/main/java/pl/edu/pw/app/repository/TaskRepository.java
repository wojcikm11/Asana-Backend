package pl.edu.pw.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.app.domain.task.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
