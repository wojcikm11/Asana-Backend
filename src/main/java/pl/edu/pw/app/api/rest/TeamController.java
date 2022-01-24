package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.service.TeamService;

//@AllArgsConstructor
@RestController
@RequestMapping(path="api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping(path="/add")
    @ResponseBody
    public TeamBasicInfo addTeam(@RequestBody TeamCreateRequest team){

        System.out.println("controller");
        return teamService.addTeam(team);
    }
}
