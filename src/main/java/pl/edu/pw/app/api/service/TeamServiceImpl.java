package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.domain.Team;
import pl.edu.pw.app.repository.TeamRepository;

@Service
//@AllArgsConstructor
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;
    private final String EMPTY_TEAM_NAME_EXCEPTION = "Team's name cannot be empty";


    @Override
    public TeamBasicInfo addTeam(TeamCreateRequest team) {
        if(team.getName() == null || team.getName().isBlank()){
            throw new IllegalArgumentException(EMPTY_TEAM_NAME_EXCEPTION);
        }

        Team newTeam = map(team);
        System.out.println("adding");
        teamRepository.save(newTeam);

        return map(newTeam);
    }




    private Team map(TeamCreateRequest team){
        return new Team(team.getName());
    }

    private TeamBasicInfo map(Team team){
        return new TeamBasicInfo(team.getName(),team.getMembers());
    }
}
