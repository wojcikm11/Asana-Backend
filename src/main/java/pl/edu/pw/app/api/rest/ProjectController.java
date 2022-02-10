package pl.edu.pw.app.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.projectDTO.*;
import pl.edu.pw.app.api.dto.teamDTO.TeamCompleteInfo;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.service.IProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path="/api/project")
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

    @PostMapping("/team")
    @PreAuthorize("@projectSecurity.isProjectMember(#addTeam.projectId) and @projectSecurity.isTeamMember(#addTeam.teamId)")
    public ResponseEntity<?> addTeamToProject(@Valid @RequestBody AddTeam addTeam) {
        projectService.addTeam(addTeam);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{projectId}/team/remove/{teamId}")
    @PreAuthorize("@projectSecurity.isProjectOwner(#projectId)")
    public ResponseEntity<?> removeTeamFromProject(@PathVariable Long projectId, @PathVariable Long teamId) {
        RemoveTeamFromProject removeTeam = new RemoveTeamFromProject(teamId, projectId);
        projectService.removeTeam(removeTeam);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{projectId}/teams")
    @PreAuthorize("@projectSecurity.isProjectMember(#projectId)")
    public Set<TeamCompleteInfo> getProjectTeams(@PathVariable Long projectId) {
        Set<TeamCompleteInfo> projectTeamMembers = projectService.getProjectTeamMembers(projectId);
        return projectTeamMembers;
    }

    @PostMapping("/member")
    @PreAuthorize("@projectSecurity.isProjectMember(#addProjectMember.projectId)")
    public ResponseEntity<?> addProjectMember(@Valid @RequestBody AddProjectMember addProjectMember) {
        projectService.addUserToProject(addProjectMember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@projectSecurity.isProjectOwner(#id)")
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectUpdateRequest projectUpdateRequest, @PathVariable Long id) {
        projectService.update(id, projectUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/delete_member/{memberId}")
    @PreAuthorize("@projectSecurity.isProjectOwner(#projectId)")
    public ResponseEntity<?> deleteProjectMember(@PathVariable Long projectId, @PathVariable Long memberId) {
        // TODO: sprawdzić, czy członek projektu był ostatnią osobą w zespole, który pracował nad projektem, jeśli tak to usuwamy zespół
        projectService.removeProjectMember(projectId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@projectSecurity.isProjectOwner(#id)")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
