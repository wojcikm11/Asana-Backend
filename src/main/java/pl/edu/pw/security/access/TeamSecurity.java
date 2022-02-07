package pl.edu.pw.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.app.api.service.TeamService;
import pl.edu.pw.app.api.service.UserService;
import pl.edu.pw.app.api.service.UtilityService;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.Team;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.TeamRepository;

@Component("teamSecurity")
public class TeamSecurity {

    private TeamRepository teamRepository;

    @Autowired
    public TeamSecurity(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public boolean isTeamOwner(Long teamId) {
        User user = UtilityService.getLoggedUser();
        Team team = teamRepository.findById(teamId).orElseThrow();
        return team.isOwner(user.getEmail());
    }

    public boolean isTeamMember(Long teamId) {
        return !isTeamOwner(teamId);
    }
}
