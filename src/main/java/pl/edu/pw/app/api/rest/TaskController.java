package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.taskDTO.TaskCreateRequest;
import pl.edu.pw.app.api.service.IProjectService;
import pl.edu.pw.app.api.service.ProjectService;
import pl.edu.pw.app.api.service.TaskService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/task")
public class TaskController {

    private TaskService taskService;

    @PostMapping("add")
    public ResponseEntity addTask(@RequestBody @Valid TaskCreateRequest task){
        System.out.println(task.getProjectId());
        taskService.addTask(task);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
