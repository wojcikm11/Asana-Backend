package pl.edu.pw.app.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
        return projectService.getAll(id);
    }

    @PostMapping
    public void addProject(@Valid @RequestBody ProjectCreateRequest project) {
        projectService.create(project);
    }

    @PostMapping("/member")
    public void addProjectMember(@Valid @RequestBody AddProjectMember addProjectMember) {
        projectService.addUserToProject(addProjectMember);
    }

    @PutMapping("/{id}")
    public void updateProject(@Valid @RequestBody ProjectUpdateRequest projectUpdateRequest, @PathVariable Long id) {
        projectService.update(id, projectUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.delete(id);
    }
}
