package pl.edu.pw.app.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.app.api.dto.taskDTO.AddTaskTimeForProjectMember;
import pl.edu.pw.app.api.service.project.ProjectTimeService;

@RestController
@RequestMapping(path="/api/project")
public class ProjectTimeController {

    private ProjectTimeService projectTimeService;

    @PostMapping("/member/time/add")
    @PreAuthorize("@taskSecurity.isProjectMember(#addTaskTime.taskId)")
    public ResponseEntity<?> addTaskTime(@RequestBody AddTaskTimeForProjectMember addTaskTime) {
        projectTimeService.addTimeToTask(addTaskTime);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
