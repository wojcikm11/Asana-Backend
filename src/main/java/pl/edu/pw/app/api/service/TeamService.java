package pl.edu.pw.app.api.service;

import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;

public interface TeamService {

    TeamBasicInfo addTeam(TeamCreateRequest team);
}