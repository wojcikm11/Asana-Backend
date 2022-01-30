package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.subtaskDTO.*;
import pl.edu.pw.app.api.service.SubtaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/project/task/subtask")
public class SubtaskController {

    private SubtaskService subtaskService;

    @PostMapping("add")
    public ResponseEntity<?> addSubtask(@Valid @RequestBody SubtaskCreateRequest subtask){
        subtaskService.addSubtask(subtask);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteSubtask(@PathVariable Long id){
        subtaskService.deleteSubtask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<?> updateSubtask(@Valid @RequestBody SubtaskUpdateRequest editedSubtask, @PathVariable Long id){
        subtaskService.updateSubtask(editedSubtask, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")  // task id
    public List<SubtaskBasicInfo> getSubtasks(@PathVariable Long id){
        return subtaskService.getSubtasks(id);
    }

    @GetMapping("all/details/{id}")
    public List<SubtaskCompleteInfo> getSubtasksDetails(@PathVariable Long id){
        return subtaskService.getSubtasksDetails(id);
    }

    @GetMapping("/details/{id}")
    public SubtaskCompleteInfo getSubtaskDetails(@PathVariable Long id){
        return subtaskService.getSubtaskDetails(id);
    }

    @PostMapping("assign")
    public ResponseEntity<?> addAssignee(@Valid @RequestBody AssignRequest assign ){
        subtaskService.addAssignee(assign);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
