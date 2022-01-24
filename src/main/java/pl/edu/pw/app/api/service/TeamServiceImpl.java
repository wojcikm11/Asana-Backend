package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.domain.Team;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.TeamRepository;
import pl.edu.pw.app.repository.UserRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {


    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private final String EMPTY_TEAM_NAME_EXCEPTION = "Team's name cannot be empty";
    private final String NOT_USER_ID = "User with given id does not exists";


    @Override
    public TeamBasicInfo addTeam(TeamCreateRequest team, Long id) {
        if (team.getName() == null || team.getName().isBlank()) {
            throw new IllegalArgumentException(EMPTY_TEAM_NAME_EXCEPTION);
        }

        Team newTeam = null;

        Optional<User> user = userRepository.findById(id);


        if(user.isPresent()){
            newTeam = new Team(team.getName(), user.get());
            teamRepository.save(newTeam);
        }else{
            throw new IllegalArgumentException(NOT_USER_ID);
        }

        return map(newTeam);
    }


    private Team map(TeamCreateRequest team) {
        return new Team(team.getName());
    }

    private TeamBasicInfo map(Team team) {
        return new TeamBasicInfo(team.getId(), team.getName(), team.getMembers());
    }
}
