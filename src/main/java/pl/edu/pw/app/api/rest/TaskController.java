package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.taskDTO.*;
import pl.edu.pw.app.api.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/project/task")
public class TaskController {

    private TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody @Valid TaskCreateRequest task){
        taskService.addTask(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskUpdateRequest editedTask, @PathVariable Long id){
        taskService.updateTask(editedTask, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path="/all/{id}")
    public List<TaskBasicInfo> getProjectTasks(@PathVariable Long id){
        return taskService.getTasks(id);
    }

    @GetMapping(path="/all/{id}/details")
    public List<TaskDetails> getProjectTasksDetails(@PathVariable Long id) {
        return taskService.getTasksDetails(id);
    }

    @PostMapping("/add/assignee")
    public ResponseEntity<?> addAssignee(@RequestBody AddAssigneeRequest assignee) {
        taskService.addAssignee(assignee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{taskId}/assignee/delete/{assigneeId}")
    public ResponseEntity<?> deleteAssignee(@PathVariable Long taskId, @PathVariable Long assigneeId) {
        taskService.removeAssignee(taskId, assigneeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
