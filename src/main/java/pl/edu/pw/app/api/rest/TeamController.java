package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.DeleteTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.service.TeamService;

import javax.validation.Valid;
import java.util.List;


@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping(path="api/team")
public class TeamController {

    private TeamService teamService;

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseEntity<?> addTeam(@RequestBody TeamCreateRequest team){
        teamService.addTeam(team);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<TeamBasicInfo> getAllTeams(){
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

    @GetMapping("/user/{id}/teams")
    public List<TeamBasicInfo> getUserTeams(@PathVariable Long id) {
        return teamService.getUserTeams(id);
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
}
