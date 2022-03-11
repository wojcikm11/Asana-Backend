package pl.edu.pw.app.api.service;

import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCompleteInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.DeleteTeamMemberRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;

import java.util.List;

public interface TeamService {
    void addTeam(TeamCreateRequest team);
    void deleteTeam(Long id);
    List<TeamMemberBasicInfo> getTeamMembers(Long teamId);
    List<TeamBasicInfo> getUserTeams(Long userId);
    void addMember(AddTeamMemberRequest addTeamMember);
    TeamCompleteInfo getTeam(Long teamId);
    List<TeamCompleteInfo> getAll();
    void deleteMember(DeleteTeamMemberRequest deleteTeamMember);
    List<ProjectCompleteInfo> getTeamProjects(Long id);
}
