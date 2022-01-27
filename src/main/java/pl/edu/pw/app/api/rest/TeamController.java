package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;
import pl.edu.pw.app.api.service.TeamService;
import pl.edu.pw.app.domain.TeamMember;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping(path="api/team")
public class TeamController {

    private TeamService teamService;

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseEntity addTeam(@RequestBody TeamCreateRequest team){

        teamService.addTeam(team);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/teams")
    public String showTeams( @PathVariable Long id){
        return "teams"+id;
    }


    @DeleteMapping("/delete")
    public ResponseEntity deleteTeam(@RequestParam Long id){
        teamService.deleteTeam(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/members")
    public List<TeamMemberBasicInfo> getTeamMembers(@RequestParam Long id) {
        return teamService.getMembers(id);

    }

    @PostMapping("/addMember")
    public ResponseEntity addMember(@RequestParam Long memberId, Long teamId){
        teamService.addMember(memberId,teamId);
        return new ResponseEntity(HttpStatus.CREATED);
    }



}
