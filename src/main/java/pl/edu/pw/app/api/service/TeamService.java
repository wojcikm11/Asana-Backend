package pl.edu.pw.app.api.service;

import org.springframework.http.ResponseEntity;
import pl.edu.pw.app.api.dto.teamDTO.TeamBasicInfo;
import pl.edu.pw.app.api.dto.teamDTO.TeamCreateRequest;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;
import pl.edu.pw.app.domain.TeamMember;

import java.util.List;

public interface TeamService {

//    user id
    void addTeam(TeamCreateRequest team, Long id);
//    team id
    void deleteTeam(Long id);

    List<TeamMemberBasicInfo> getMembers(Long id);

   void addMember(Long memberId, Long teamId);
}
