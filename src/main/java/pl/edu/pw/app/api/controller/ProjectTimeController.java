package pl.edu.pw.app.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;
import pl.edu.pw.app.api.dto.timeDTO.ProjectTasksTime;
import pl.edu.pw.app.api.service.project.ProjectTimeService;

@RestController
@RequestMapping(path="/api/project")
public class ProjectTimeController {

    private ProjectTimeService projectTimeService;

    @Autowired
    public ProjectTimeController(ProjectTimeService projectTimeService) {
        this.projectTimeService = projectTimeService;
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
}
