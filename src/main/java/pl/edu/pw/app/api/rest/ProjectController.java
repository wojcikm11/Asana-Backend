package pl.edu.pw.app.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.projectDTO.AddProjectMember;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCreateRequest;
import pl.edu.pw.app.api.dto.projectDTO.ProjectUpdateRequest;
import pl.edu.pw.app.api.service.IProjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="api/project")
public class ProjectController {

    private IProjectService projectService;

    @Autowired
    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public ProjectCompleteInfo getProject(@PathVariable Long id) {
        return projectService.get(id);
    }

    @GetMapping("/owner/{id}")
    public List<ProjectCompleteInfo> getOwnerProjects(@PathVariable Long id){
        return projectService.getUserProjects(id);
    }

    @PostMapping
    public ResponseEntity<?> addProject(@Valid @RequestBody ProjectCreateRequest project) {
        projectService.create(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/member")
    @PreAuthorize("@projectSecurity.isProjectOwner(#addProjectMember.projectId)")
    public ResponseEntity<?> addProjectMember(@Valid @RequestBody AddProjectMember addProjectMember) {
        projectService.addUserToProject(addProjectMember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectUpdateRequest projectUpdateRequest, @PathVariable Long id) {
        projectService.update(id, projectUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/delete_member/{memberId}")
    public ResponseEntity<?> deleteProjectMember(@PathVariable Long projectId, @PathVariable Long memberId) {
        projectService.removeProjectMember(projectId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
