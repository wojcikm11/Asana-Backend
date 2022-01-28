package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.taskDTO.TaskBasicInfo;
import pl.edu.pw.app.api.dto.taskDTO.TaskCreateRequest;
import pl.edu.pw.app.api.dto.taskDTO.TaskDetails;
import pl.edu.pw.app.api.service.IProjectService;
import pl.edu.pw.app.api.service.ProjectService;
import pl.edu.pw.app.api.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/project/")
public class TaskController {

    private TaskService taskService;

    @PostMapping("task/add")
    public ResponseEntity addTask(@RequestBody @Valid TaskCreateRequest task){
        System.out.println(task.getProjectId());
        taskService.addTask(task);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(path="{id}/tasks")
    public List<TaskBasicInfo> getProjectTasks(@PathVariable Long id){
        return taskService.getTasks(id);
    }

  @GetMapping(path="{id}/tasks/details")
    public List<TaskDetails> getProjectTasksDetails(@PathVariable Long id){
        return taskService.getTasksDetails(id);
  }
}
