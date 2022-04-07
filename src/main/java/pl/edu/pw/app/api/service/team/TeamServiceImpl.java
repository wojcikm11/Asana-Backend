package pl.edu.pw.app.api.service.team;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCompleteInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.DeleteTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.service.common.UtilityService;
import pl.edu.pw.app.api.service.project.ProjectServiceImpl;
import pl.edu.pw.app.domain.team.Team;
import pl.edu.pw.app.domain.team.TeamMember;
import pl.edu.pw.app.domain.user.User;
import pl.edu.pw.app.repository.TeamRepository;
import pl.edu.pw.app.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static pl.edu.pw.app.api.service.project.ProjectServiceImpl.ProjectMapper.map;
import static pl.edu.pw.app.api.service.team.TeamServiceImpl.TeamMapper.mapToBasic;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class TeamServiceImpl implements TeamService {
    private TeamRepository teamRepository;
    private UserRepository userRepository;

    private final String EMPTY_TEAM_NAME_EXCEPTION = "Team's name cannot be empty";
    private final String USER_NOT_FOUND_EXCEPTION = "User with given id does not exist";
    private final String TEAM_NOT_FOUND_EXCEPTION = "Team with the given id not found";
    private final String NO_PERMISSION_EXCEPTION = "You do not have permission to execute this action";


    @Override
    public Long addTeam(TeamCreateRequest team){
        if (team.getName() == null || team.getName().isBlank()) {
            throw new IllegalArgumentException(EMPTY_TEAM_NAME_EXCEPTION);
        }

        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException(USER_NOT_FOUND_EXCEPTION);
        });

        List<Long> list = team.getMembers();
        Team newTeam = new Team(team.getName(), user);
        teamRepository.save(newTeam);

        if(!list.isEmpty()){
            for(Long member : list){
                User u =userRepository.findById(member).orElseThrow(
                        () -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION)
                );

                newTeam.addMember(u);
            }





        }
        return newTeam.getId();
    }

    @Override
    public List<ProjectCompleteInfo> getTeamProjects(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Team with id "+id+" does not exist"));
        List<ProjectCompleteInfo> projects =  team.getProjects().stream().map(m->{
            return ProjectServiceImpl.ProjectMapper.map(m);
        }).collect(toList());

        return projects;
    }

    @Override
    public void editTeam(Long id, TeamCreateRequest createTeam) {
        Team team = teamRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Team with id "+id+" does not exist"));

        if(createTeam.getMembers().size()==0){
            teamRepository.deleteById(id);
            return;
        }

        team.setName(createTeam.getName());
        List<Long> actualTeamMembersIds = createTeam.getMembers();
        Iterator<TeamMember> teamMemberIterator = team.getMembers().iterator();
        while (teamMemberIterator.hasNext()) {
            TeamMember teamMember = teamMemberIterator.next();
            if (currentTeamMemberNotInNewTeam(actualTeamMembersIds, teamMember)) {
                teamMemberIterator.remove();
            } else {
                actualTeamMembersIds.remove(teamMember.getId().getMemberId()); // teamMember jest już w teamie i ma w nim być, więc usuwamy go by nie dodać go w następnej pętli
            }
        }
        for (Long userId : actualTeamMembersIds) {
            User userToAddToTeam = userRepository.findById(userId).orElseThrow();
            team.addMember(userToAddToTeam);
        }


//        Map<Long, TeamMember.Role> newMembers=new HashMap();
//        Set<Long> mm =new HashSet(createTeam.getMembers());
//
//       createTeam.getMembers().forEach(m->{
//           log.info("memeber id: {}",m);
//       });
//
//        createTeam.getMembers().forEach(m->{
//            team.getMembers().forEach(mem->{
//                if(m==mem.getUser().getId()){
//                    if(mem.getRole().toString()=="OWNER"){
//                        newMembers.put(m, TeamMember.Role.OWNER);
//                        mm.remove(m);
//                        log.info("Found owner: {}",mem.getUser().getEmail());
//                    }else{
//                        log.info("Found member: {}",mem.getUser().getEmail());
//                        newMembers.put(m, TeamMember.Role.MEMBER);
//                        mm.remove(m);
//                    }
//
//                }
//
//            });
//
//
//        });
//
//        for(Map.Entry<Long, TeamMember.Role> m :newMembers.entrySet()){
//          log.info("New set of members: "+userRepository.getById(m.getKey()).getEmail());
//        }
//        for(Long i : mm)
//            log.info("New set of members: "+userRepository.getById(i).getEmail());
//
//
//        for(Map.Entry<Long, TeamMember.Role> m :newMembers.entrySet()){
//            log.info("Deleting member: "+userRepository.getById(m.getKey()).getEmail());
//            team.removeMember(userRepository.getById(m.getKey()),m.getValue());
//        }
//        List<TeamMember> membersToDelete = new ArrayList(newMembers.values());
//        membersToDelete.stream().forEach(m->{
//            log.info("Deleting "+m.getUser().getEmail());
//            team.removeMember(userRepository.getById(m.getUser().getId()),m);
//        });


//        List<TeamMember> members = new ArrayList<>();
//              for(Map.Entry<Long, TeamMember.Role> m :newMembers.entrySet()){
//                  members.add(new TeamMember(userRepository.getById(m.getKey()),team,m.getValue()));
//        }

//         team.setMembers(members);

    }

    private boolean currentTeamMemberNotInNewTeam(List<Long> actualTeamMembersIds, TeamMember teamMember) {
        return !actualTeamMembersIds.contains(teamMember.getId().getMemberId());
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
        boolean isOwner;
        User user = userRepository.findById(userId).orElseThrow();
        return user.getTeams().stream().map(teamMember ->{
           if(teamMember.getRole() == TeamMember.Role.OWNER){
               return mapToBasic(teamMember.getTeam(),true);
           }
           return mapToBasic(teamMember.getTeam(),false);
        }).collect(toList());

    }

    @Override
    public TeamCompleteInfo getTeam(Long teamId) {
        return map(teamRepository.getById(teamId));
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
    public List<TeamCompleteInfo> getAll() {
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

    private TeamCompleteInfo map(Team team) {
        return new TeamCompleteInfo(team.getId(), team.getName(), map(team.getMembers()),
                team.getProjects().stream().map(p->{
                    return ProjectServiceImpl.ProjectMapper.map(p,UtilityService.getLoggedUser().getId());
                }).collect(toList())
                );
    }

    private TeamCompleteInfo map(TeamMember teamMember) {
        Team userTeam = teamMember.getTeam();
        return new TeamCompleteInfo(userTeam.getId(), userTeam.getName(), map(userTeam.getMembers()));
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

    public static class TeamMapper {
        public static TeamMemberBasicInfo map(TeamMember teamMember) {
            return new TeamMemberBasicInfo(
                    teamMember.getId().getMemberId(),
                    teamMember.getUser().getName(),
                    teamMember.getUser().getEmail(),
                    teamMember.getRole()
            );
        }


        public static TeamBasicInfo mapToBasic(Team team,boolean isOwner){

            return new TeamBasicInfo(
                    team.getId(),
                    team.getName(),
                    isOwner
            );
        }
    }
}
