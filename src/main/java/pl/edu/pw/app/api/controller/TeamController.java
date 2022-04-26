package pl.edu.pw.app.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCompleteInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.DeleteTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.service.team.TeamService;

import javax.validation.Valid;
import java.util.List;


@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping(path="api/team")
public class TeamController {

    private TeamService teamService;

    @PostMapping(path="/add",consumes="application/json")
    public Long addTeam(@RequestBody TeamCreateRequest team){
        return teamService.addTeam(team);

    }

    @GetMapping("/{id}/projects")
//    @PreAuthorize("@teamSecurity.isTeamMember(#id)")
    public List<ProjectCompleteInfo> getTeamProjects(@PathVariable Long id){
        return teamService.getTeamProjects(id);
    }


    @GetMapping
    public List<TeamCompleteInfo> getAllTeams(){
        return teamService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@teamSecurity.isTeamOwner(#id)")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/members")
    public List<TeamMemberBasicInfo> getTeamMembers(@PathVariable Long id) {
        return teamService.getTeamMembers(id);
    }


    @PreAuthorize("@userSecurity.isLoggedInUser(#id)")
    @GetMapping("/user/{id}/teams")
    public List<TeamBasicInfo> getUserTeams(@PathVariable Long id) {
        return teamService.getUserTeams(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize(("@teamSecurity.isTeamMember(#id)"))
    public TeamCompleteInfo getTeam(@PathVariable Long id) {
        return teamService.getTeam(id);
    }

    @PostMapping("/addMember")
    @PreAuthorize("@teamSecurity.isTeamOwner(#addTeamMember.teamId)")
    public ResponseEntity<?> addMember(@RequestBody @Valid AddTeamMemberRequest addTeamMember){
        teamService.addMember(addTeamMember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteMember")
    @PreAuthorize("@teamSecurity.isTeamOwner(#deleteTeamMember.teamId)")
    public ResponseEntity<?> deleteMember(@RequestBody @Valid DeleteTeamMemberRequest deleteTeamMember){
        teamService.deleteMember(deleteTeamMember);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editTeam(@PathVariable Long id , @RequestBody TeamCreateRequest team){
        teamService.editTeam(id,team);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{teamId}/leave/{userId}")
    @PreAuthorize("@teamSecurity.isTeamMember(#teamId)")
    public ResponseEntity<?> leaveTeam(@PathVariable Long teamId, @PathVariable Long userId){
        teamService.leaveTeam(teamId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
