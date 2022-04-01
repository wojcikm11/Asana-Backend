package pl.edu.pw.app.api.service.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.*;
import pl.edu.pw.app.api.dto.teamDTO.TeamCompleteInfo;
import pl.edu.pw.app.api.dto.teamMemberDTO.TeamMemberBasicInfo;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;
import pl.edu.pw.app.api.service.team.TeamServiceImpl;
import pl.edu.pw.app.api.service.common.UtilityService;
import pl.edu.pw.app.domain.project.Project;
import pl.edu.pw.app.domain.project.ProjectMember;
import pl.edu.pw.app.domain.team.Team;
import pl.edu.pw.app.domain.team.TeamMember;
import pl.edu.pw.app.domain.user.User;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.TaskRepository;
import pl.edu.pw.app.repository.TeamRepository;
import pl.edu.pw.app.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static pl.edu.pw.app.api.service.project.ProjectServiceImpl.ProjectMapper.map;

@Slf4j
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private TeamRepository teamRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, TeamRepository teamRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public ProjectCreateRequest create(ProjectCreateRequest project) {
        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException("User with given id does not exist");
        });
        Long createdProjectId = projectRepository.save(map(project, user)).getId();
        project.setId(createdProjectId);
        return project;
    }

    @Override
    public ProjectCompleteInfo get(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ProjectMapper::map).orElseThrow();
    }

    @Override
    public List<ProjectBasicInfo> getUserProjects(Long userId) {
        return userRepository.findById(userId).orElseThrow().getProjects().stream()
                .map(ProjectMember::getProject)
                .map(m->{
                    return map(m,userId);
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProjectCompleteInfo> getOwnerProjects(Long userId) {
        return projectRepository.findAll().stream()
                .filter(project -> project.getOwner().getUser().getId().equals(userId))
                .map(ProjectMapper::map).toList();
    }

    @Override
    public void update(Long projectId, ProjectUpdateRequest projectUpdate) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        project.setName(projectUpdate.getName());
        project.setCategory(projectUpdate.getCategory());
        project.setDescription(projectUpdate.getDescription());
        projectRepository.save(project);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void addUserToProject(AddProjectMember addProjectMember) {
        User userToAdd = userRepository.findById(addProjectMember.getUserId()).orElseThrow();
        Project project = projectRepository.findById(addProjectMember.getProjectId()).orElseThrow();
        project.addProjectMember(userToAdd);
    }

    @Override
    public void removeProjectMember(Long projectId, Long userId) {
        User member = userRepository.findById(userId).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();
        if (project.getOwner().getId().getMemberId().equals(userId)) {
            throw new IllegalArgumentException("Project owner cannot remove himself from the project");
        }
        project.removeProjectMember(member);
        removeTeamFromProjectIfNoTeamMembersLeft(project, member);
    }

    private void removeTeamFromProjectIfNoTeamMembersLeft(Project project, User removedProjectMember) {
        for (Team team : project.getTeams()) {
            if (team.getMembers().stream().noneMatch(teamMember -> teamMember.getUser().equals(removedProjectMember))) {
                project.removeTeam(team);
            }
        }
    }

    @Override
    public void addTeam(AddTeam addTeam) {
        Team team = teamRepository.findById(addTeam.getTeamId()).orElseThrow();
        Project project = projectRepository.findById(addTeam.getProjectId()).orElseThrow();
        project.addTeam(team); // do usuniecia, jesli usuniemy tabele
        addTeamMembersToProject(project, team);
    }

    @Override
    public void removeTeam(RemoveTeamFromProject removeTeam) {
        Team team = teamRepository.findById(removeTeam.getTeamId()).orElseThrow();
        Project project = projectRepository.findById(removeTeam.getProjectId()).orElseThrow();
        project.removeTeam(team); // do usuniecia, jesli usuniemy tabele
        removeTeamMembersFromProject(project, team);
    }

    @Override
    public Set<TeamCompleteInfo> getProjectTeamMembers(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        Set<Team> projectTeams = project.getTeams();
        Set<List<TeamMember>> filteredTeamMembers = new HashSet<>();
        for (Team team : projectTeams) {
            List<TeamMember> teamMembers = team.getMembers().stream()
                                        .filter(teamMember -> teamMember.getUser().getProjects()
                                        .contains(project.getProjectMemberByUserId(teamMember.getId().getMemberId()))).toList();
            filteredTeamMembers.add(teamMembers);
        }
        return filteredTeamMembers.stream().map(ProjectMapper::map).collect(Collectors.toSet());
    }

    @Override
    public Set<ProjectMemberInfo> getProjectNonTeamMembers(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        Set<Team> projectTeams = project.getTeams();
        List<ProjectMember> projectMembersFiltered = new ArrayList<>();
        for (ProjectMember projectMember : project.getMembers()) {
            boolean isInTeam = false;
            for (Team team : projectTeams) {
                if (team.getMembers().contains(team.getTeamMemberByUserId(projectMember.getId().getMemberId()))) {
                    isInTeam = true;
                    break;
                }
            }
            if (!isInTeam) {
                projectMembersFiltered.add(projectMember);
            }
        }
        return projectMembersFiltered.stream().map(ProjectMapper::map).collect(Collectors.toSet());
    }

    @Override
    public List<ProjectMemberInfo> getAllProjectMembers(Long projectId) {
        Project project = projectRepository.getById(projectId);
        return project.getMembers().stream().map(ProjectMapper::map).collect(Collectors.toList());
    }


    private void removeTeamMembersFromProject(Project project, Team team) {
        for (TeamMember teamMember : team.getMembers()) {
            ProjectMember projectMember = project.getProjectMemberByUserId(teamMember.getUser().getId());
            if (projectMember != null && projectMember.getRole().equals(ProjectMember.Role.MEMBER)) {
                project.removeProjectMember(teamMember.getUser());
            }
        }
    }

    private void addTeamMembersToProject(Project project, Team team) {
        for (TeamMember teamMember : team.getMembers()) {
            ProjectMember projectMember = project.getProjectMemberByUserId(teamMember.getUser().getId());
            if (projectMember == null) {
                project.addProjectMember(teamMember.getUser());
            }
        }
    }

    public static class ProjectMapper {
        public static ProjectCompleteInfo map(Project project) {
            return new ProjectCompleteInfo(
                    project.getId(),
                    project.getName(),
                    project.getCategory(),
                    project.getDescription(),
                    project.getMembers().stream().map(ProjectMapper::map).toList()
            );
        }

        public static ProjectBasicInfo map(Project project,Long id){
            return new ProjectBasicInfo(
                    Math.toIntExact(project.getId()),
                    project.getName(),
                    project.getDescription(),
                    project.getProjectMemberByUserId(id).getRole().toString()=="OWNER"
            );
        }

        public static ProjectMemberInfo map(ProjectMember projectMember) {
            UserBasicInfo userBasicInfo = new UserBasicInfo(projectMember.getId().getMemberId(),
                    projectMember.getUser().getName(),
                    projectMember.getUser().getEmail());
            return new ProjectMemberInfo(userBasicInfo, projectMember.getRole().name());
        }

        public static Project map(ProjectCreateRequest project, User owner) {
            return new Project(
                    owner,
                    project.getName(),
                    project.getCategory(),
                    project.getDescription()
            );
        }

        public static TeamCompleteInfo map(Team team) {
            return new TeamCompleteInfo(
                team.getId(),
                team.getName(),
                team.getMembers().stream().map(TeamServiceImpl.TeamMapper::map).collect(Collectors.toList())
            );
        }

        public static TeamCompleteInfo map(List<TeamMember> teamMembers) {
            Long teamId = teamMembers.get(0).getTeam().getId();
            String teamName = teamMembers.get(0).getTeam().getName();
            List<TeamMemberBasicInfo> teamMemberBasicInfoList = teamMembers.stream().map(TeamServiceImpl.TeamMapper::map).toList();

            return new TeamCompleteInfo(teamId, teamName, teamMemberBasicInfoList);
        }
    }
}
