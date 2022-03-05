package pl.edu.pw.app.api.service;

import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.DeleteTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;
import pl.edu.pw.app.domain.TeamMember;

import java.util.List;

public interface TeamService {
    void addTeam(TeamCreateRequest team);
    void deleteTeam(Long id);
    List<TeamMemberBasicInfo> getTeamMembers(Long teamId);
    List<TeamBasicInfo> getUserTeams(Long userId);
    void addMember(AddTeamMemberRequest addTeamMember);
    TeamBasicInfo getTeam(Long teamId);
    List<TeamBasicInfo> getAll();
    void deleteMember(DeleteTeamMemberRequest deleteTeamMember);
    List<ProjectCompleteInfo> getTeamProjects(Long id);
}
