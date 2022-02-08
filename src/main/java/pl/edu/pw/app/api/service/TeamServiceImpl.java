package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.ProjectBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.DeleteTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.domain.Team;
import pl.edu.pw.app.domain.TeamMember;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.TeamRepository;
import pl.edu.pw.app.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {
    private TeamRepository teamRepository;
    private UserRepository userRepository;

    private final String EMPTY_TEAM_NAME_EXCEPTION = "Team's name cannot be empty";
    private final String USER_NOT_FOUND_EXCEPTION = "User with given id does not exist";
    private final String TEAM_NOT_FOUND_EXCEPTION = "Team with the given id not found";
    private final String NO_PERMISSION_EXCEPTION = "You do not have permission to execute this action";


    @Override
    public void addTeam(TeamCreateRequest team){
        if (team.getName() == null || team.getName().isBlank()) {
            throw new IllegalArgumentException(EMPTY_TEAM_NAME_EXCEPTION);
        }
        System.out.println(UtilityService.getCurrentUser());
        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException(USER_NOT_FOUND_EXCEPTION);
        });

        Team newTeam = new Team(team.getName(), user);
        teamRepository.save(newTeam);
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.getById(id);
        boolean isOwner = team.isOwner(UtilityService.getCurrentUser());
        if(isOwner){
            teamRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException(TEAM_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public List<TeamMemberBasicInfo> getTeamMembers(Long teamId) {
        Team team = teamRepository.getById(teamId);
        return map(team.getMembers());
    }

    @Override
    public List<TeamBasicInfo> getUserTeams(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getTeams().stream().map(this::map).toList();
    }

    @Override
    public void addMember(AddTeamMemberRequest addTeamMember) {
        Team team = teamRepository.findById(addTeamMember.getTeamId()).orElseThrow(
                () -> new EntityNotFoundException(TEAM_NOT_FOUND_EXCEPTION)
        );

        if(!team.isOwner(UtilityService.getCurrentUser())){
            throw new EntityNotFoundException(NO_PERMISSION_EXCEPTION);
        }

        User user =userRepository.findById(addTeamMember.getMemberId()).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION)
        );
       team.addMember(user);
    }

    @Override
    public List<TeamBasicInfo> getAll() {
        return teamRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public void deleteMember(DeleteTeamMemberRequest deleteTeamMember) {
        Team team = teamRepository.findById(deleteTeamMember.getTeamId()).orElseThrow(
                () -> new EntityNotFoundException(TEAM_NOT_FOUND_EXCEPTION)
        );

        if (team.getOwner().getId().getMemberId().equals(deleteTeamMember.getMemberId())) {
            throw new IllegalArgumentException("Team owner cannot remove himself from the team");
        }

        if(!team.isOwner(UtilityService.getCurrentUser())){
            throw new EntityNotFoundException(NO_PERMISSION_EXCEPTION);
        }
        User user =userRepository.findById(deleteTeamMember.getMemberId()).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION)
        );
      TeamMember member = team.getMembers().stream().filter(
              m ->
                 m.getUser().getId() == deleteTeamMember.getMemberId()
      ).findFirst().orElseThrow(
              ()-> new IllegalArgumentException(USER_NOT_FOUND_EXCEPTION)



      );


        team.getMembers().remove(member);
        teamRepository.save(team);
    }

    private Team map(TeamCreateRequest team) {
        return new Team(team.getName());
    }

    private TeamBasicInfo map(Team team) {
        return new TeamBasicInfo(team.getId(), team.getName(), map(team.getMembers()));
    }

    private TeamBasicInfo map(TeamMember teamMember) {
        Team userTeam = teamMember.getTeam();
        return new TeamBasicInfo(userTeam.getId(), userTeam.getName(), map(userTeam.getMembers()));
    }

    private List<TeamMemberBasicInfo> map(List<TeamMember> members){
        List<TeamMemberBasicInfo> users = new ArrayList<>();
        members.forEach(m-> {
            users.add(
                    new TeamMemberBasicInfo(m.getUser().getId(),m.getUser().getName(),m.getUser().getEmail(),m.getRole())
            );
        });
        return users;
    }
}
