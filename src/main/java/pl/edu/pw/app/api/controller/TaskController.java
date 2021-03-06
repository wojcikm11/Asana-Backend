package pl.edu.pw.app.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.taskDTO.*;
import pl.edu.pw.app.api.dto.timeDTO.SetTimeRequest;
import pl.edu.pw.app.api.service.task.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/project/task")
public class TaskController {

    private TaskService taskService;

    @PutMapping("/postponedeadlines/{projectId}")
    @PreAuthorize("@projectSecurity.isProjectMember(#projectId)")
    public ResponseEntity<?> postponeProjectTasksDeadlines(@PathVariable Long projectId,@RequestBody PostponeDeadlinesRequest time ){
        taskService.postponeDeadlines(projectId,time);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/add")
    @PreAuthorize("@projectSecurity.isProjectMember(#task.projectId)")
    public ResponseEntity<?> addTask(@RequestBody @Valid TaskCreateRequest task){
        taskService.addTask(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@taskSecurity.isProjectMember(#id)")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@taskSecurity.isProjectMember(#id)")
    public TaskDetails getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("@taskSecurity.isProjectMember(#id)")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskUpdateRequest editedTask, @PathVariable Long id){
        taskService.updateTask(editedTask, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit/status/{id}")
    @PreAuthorize("@taskSecurity.isProjectMember(#id)")
    public ResponseEntity<?> updateTaskStatus(@Valid @RequestBody TaskStatusUpdateRequest taskStatus, @PathVariable Long id){
        taskService.updateTaskStatus(taskStatus, id);
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
    @PreAuthorize("@taskSecurity.isProjectMember(#assignee.taskId)")
    public ResponseEntity<?> addAssignee(@RequestBody AddAssigneeRequest assignee) {
        taskService.addAssignee(assignee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{taskId}/assignee/delete/{assigneeId}")
    @PreAuthorize("@taskSecurity.isProjectMember(#taskId)")
    public ResponseEntity<?> deleteAssignee(@PathVariable Long taskId, @PathVariable Long assigneeId) {
        taskService.removeAssignee(taskId, assigneeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}/settime")
    @PreAuthorize("@taskSecurity.isProjectMember(#taskId)")
    public ResponseEntity<?> setTimer(@PathVariable Long taskId, @RequestBody SetTimeRequest time){
        taskService.setTime(taskId,time);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/assigned")
    public List<TaskDetails> getUserAssignedTasks() {
        return taskService.getUserAssignedTasks();
    }
}
