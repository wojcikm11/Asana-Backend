package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.domain.Team;
import pl.edu.pw.app.domain.TeamMember;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.TeamRepository;
import pl.edu.pw.app.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {


    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private final String EMPTY_TEAM_NAME_EXCEPTION = "Team's name cannot be empty";
    private final String USER_NOT_FOUND_EXCEPTION = "User with given id does not exists";
    private final String TEAM_NOT_FOUND_EXCEPTION = "Team with the given id not found";
    private final String NO_PERMISSION_EXCEPTION = "You do not have permission to execute this action";


    @Override
    public void addTeam(TeamCreateRequest team ){
        if (team.getName() == null || team.getName().isBlank()) {
            throw new IllegalArgumentException(EMPTY_TEAM_NAME_EXCEPTION);
        }


        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException(USER_NOT_FOUND_EXCEPTION);
        });

            Team newTeam = new Team(team.getName(),user);
            newTeam.addMember(user, TeamMember.Role.OWNER);
            teamRepository.save(newTeam);
    }

    public void deleteTeam(Long id){

        Team team = teamRepository.getById(id);
        boolean isOwner = team.isOwner(UtilityService.getCurrentUser());
        if(isOwner){
            teamRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException(TEAM_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public List<TeamMemberBasicInfo> getMembers(Long id) {
        Team team = teamRepository.getById(id);
        return map(team.getMembers());
    }

    @Override
    public void addMember(Long memberId, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new EntityNotFoundException(TEAM_NOT_FOUND_EXCEPTION)
        );

        if(!team.isOwner(UtilityService.getCurrentUser())){
            throw new EntityNotFoundException(NO_PERMISSION_EXCEPTION);
        }

        User user =userRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION)
        );


       team.addMember(user);



    }

    private Team map(TeamCreateRequest team) {
        return new Team(team.getName());
    }
//
//    private TeamBasicInfo map(Team team) {
//        return new TeamBasicInfo(team.getId(), team.getName(), team.getMembers());
//    }

    private List<TeamMemberBasicInfo> map(List<TeamMember> members){
        List<TeamMemberBasicInfo> users = new ArrayList<>();
        members.stream().forEach(m->{
            users.add(
                    new TeamMemberBasicInfo(m.getUser().getId(),m.getUser().getName(),m.getUser().getEmail(),m.getRole())
            );

        });
        return users;
    }
}
