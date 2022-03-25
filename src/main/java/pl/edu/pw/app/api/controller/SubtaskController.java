package pl.edu.pw.app.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.subtaskDTO.*;
import pl.edu.pw.app.api.service.task.SubtaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/project/task/subtask")
public class SubtaskController {

    private SubtaskService subtaskService;

    @PostMapping("/add")
    @PreAuthorize("@taskSecurity.isProjectMember(#subtask.id)")
    public ResponseEntity<?> addSubtask(@Valid @RequestBody SubtaskCreateRequest subtask){
        subtaskService.addSubtask(subtask);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@subtaskSecurity.isProjectMember(#id)")
    public ResponseEntity<?> deleteSubtask(@PathVariable Long id){
        subtaskService.deleteSubtask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("@subtaskSecurity.isProjectMember(#id)")
    public ResponseEntity<?> updateSubtask(@Valid @RequestBody SubtaskUpdateRequest editedSubtask, @PathVariable Long id){
        subtaskService.updateSubtask(editedSubtask, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/{id}")  // task id
    public List<SubtaskBasicInfo> getProjectSubtasks(@PathVariable Long id){
        return subtaskService.getSubtasks(id);
    }

    @GetMapping("/all/details/{id}")
    public List<SubtaskCompleteInfo> getProjectSubtasksDetails(@PathVariable Long id){
        return subtaskService.getSubtasksDetails(id);
    }

    @GetMapping("/details/{id}")
    public SubtaskCompleteInfo getSubtaskDetails(@PathVariable Long id){
        return subtaskService.getSubtaskDetails(id);
    }

    @PostMapping("/assign")
    @PreAuthorize("@subtaskSecurity.isProjectMember(#assign.subtaskId)")
    public ResponseEntity<?> addAssignee(@Valid @RequestBody AssignRequest assign){
        subtaskService.addAssignee(assign);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{subtaskId}/assignee/delete/{assigneeId}")
    @PreAuthorize("@subtaskSecurity.isProjectMember(#subtaskId)")
    public ResponseEntity<?> deleteAssignee(@PathVariable Long subtaskId, @PathVariable Long assigneeId) {
        subtaskService.removeAssignee(subtaskId, assigneeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
