package pl.edu.pw.app.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;
import pl.edu.pw.app.api.dto.taskDTO.TaskTimeAdd;
import pl.edu.pw.app.api.dto.timeDTO.ProjectTasksTime;
import pl.edu.pw.app.api.service.project.ProjectTimeService;
import pl.edu.pw.app.api.service.task.TaskService;

@RestController
@RequestMapping(path="/api/project")
public class ProjectTimeController {

    private ProjectTimeService projectTimeService;
    private TaskService taskService;

    @Autowired
    public ProjectTimeController(ProjectTimeService projectTimeService, TaskService taskService) {
        this.projectTimeService = projectTimeService;
        this.taskService = taskService;
    }

    @PostMapping("/member/time/add")
    @PreAuthorize("@taskSecurity.isProjectMember(#addTaskTime.taskId)")
    public ResponseEntity<?> addTaskTime(@RequestBody AddTaskTimeForProjectMember addTaskTime) {
        projectTimeService.addTimeToTask(addTaskTime);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{projectId}/member/time")
    @PreAuthorize("@projectSecurity.isProjectMember(#projectId)")
    public ProjectTasksTime getProjectTasksTime(@PathVariable Long projectId) {
        return projectTimeService.getProjectTasksTime(projectId);
    }

    @PutMapping("/task/{id}/time/add")
    @PreAuthorize("@taskSecurity.isProjectMember(#id)")
    public ResponseEntity<?> addTime(@PathVariable Long id, @RequestBody TaskTimeAdd taskTimeAdd) {
        taskService.addTime(id, taskTimeAdd);
        projectTimeService.addTimeToTask(new AddTaskTimeForProjectMember(id, taskTimeAdd.getTimeToAdd()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
