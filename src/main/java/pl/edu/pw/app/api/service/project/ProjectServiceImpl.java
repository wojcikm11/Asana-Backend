package pl.edu.pw.app.api.service.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.*;
import pl.edu.pw.app.api.dto.teamDTO.AddTeamToProject;
import pl.edu.pw.app.api.dto.teamDTO.TeamCompleteInfo;
import pl.edu.pw.app.api.dto.teamMemberDTO.AddMember;
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
    public ProjectCreateRequest create(ProjectCreateRequest createProject) {
        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException("User with given id does not exist");
        });



        Set<Team> teamsToAdd = new HashSet<>();
        if (createProject.getProjectTeamsToAdd() != null) {
            for (AddTeamToProject projectTeamToAdd : createProject.getProjectTeamsToAdd()) {
                Team team = teamRepository.findById(projectTeamToAdd.getTeamId()).orElseThrow();
                System.out.println("Dodaje team id " + team.getId());
                teamsToAdd.add(team);
            }
        }

        Set<User> usersToAdd = new HashSet<>();
        if (createProject.getMembersToAdd() != null) {
            for (AddMember memberToAdd : createProject.getMembersToAdd()) {
                User userToAdd = userRepository.findById(memberToAdd.getMemberId()).orElseThrow();
                usersToAdd.add(userToAdd);
            }
        }
        Long createdProjectId = projectRepository.save(map(createProject, user, teamsToAdd, usersToAdd)).getId();
        createProject.setId(createdProjectId);
        return createProject;
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

        System.out.println(projectUpdate.getProjectTeamsToAdd().isEmpty());
        System.out.println(projectUpdate.getMembersToAdd().isEmpty());
        if (projectUpdate.getProjectTeamsToAdd().isEmpty() && projectUpdate.getMembersToAdd().isEmpty())
            projectRepository.delete(project);

        List<Long> actualMembersIds = projectUpdate.getMembersToAdd().stream().map(AddMember::getMemberId).collect(Collectors.toList());
        List<Long> actualTeamsIds = projectUpdate.getProjectTeamsToAdd().stream().map(AddTeamToProject::getTeamId).collect(Collectors.toList());

        Iterator<ProjectMember> memberIterator = project.getMembers().iterator();
        while (memberIterator.hasNext()) {
            ProjectMember projectMember = memberIterator.next();
            if (currentProjectMemberNotInNewMembers(actualMembersIds, projectMember) &&
                    projectMember.getUser().getTeams().stream().map(teamMember -> teamMember.getTeam().getId()).noneMatch(actualTeamsIds::contains)) {
                memberIterator.remove();
            } else {
                actualMembersIds.remove(projectMember.getId().getMemberId());
            }
        }

        Set<Team> teamsCopy = new HashSet<>(project.getTeams());
        for (Team team : teamsCopy) {
            if (currentTeamNotInNewTeams(actualTeamsIds, team)) {
                project.detachTeam(team);
            }
        }

        for (Long teamId : actualTeamsIds) {
            Team teamToAddToProject = teamRepository.findById(teamId).orElseThrow();
            project.addTeam(teamToAddToProject);
        }

        for (Long memberId : actualMembersIds) {
            User userToAdd = userRepository.findById(memberId).orElseThrow();
            project.addProjectMember(userToAdd);
        }

        project.setName(projectUpdate.getName());
        project.setCategory(projectUpdate.getCategory());
        project.setDescription(projectUpdate.getDescription());

        if (project.getMembers().size() == 1){
            project.getMembers().get(0).setRole(ProjectMember.Role.OWNER);
        }
    }

    private boolean currentProjectMemberNotInNewMembers(List<Long> actualMembersIds, ProjectMember projectMember) {
        return !actualMembersIds.contains(projectMember.getId().getMemberId());
    }

    private boolean currentTeamNotInNewTeams(List<Long> actualTeamsIds, Team team) {
        return !actualTeamsIds.contains(team.getId());
    }

    @Override
    public void delete(Long id) {
        Project project = projectRepository.findById(id).orElseThrow();

        for (User user : project.getUsersFavouritePosts()) {
            user.removeFromFavorites(project);
        }

        List<Team> teamsToRemove = new ArrayList<>(project.getTeams());
        for (Team team : teamsToRemove) {
            team.removeProject(project);
        }
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

    @Override
    public void leaveProject(Long projectId, Long userId) {
        Project project = projectRepository.getById(projectId);
        ProjectMember projectMember = project.getProjectMemberByUserId(userId);
        if (projectMember != null && projectMember.getRole().equals(ProjectMember.Role.MEMBER)) {
            project.removeProjectMember(projectMember.getUser());
        }
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
            ProjectMember projectMember = project.getProjectMemberByUserId(id);
            boolean isOwner = false;
            Long teamId = null;
            String teamName = null;
            Set<Team> teams = project.getTeams();

            if(teams.size() > 0) {
                Team mainTeam = teams.stream().findFirst().orElse(null);;
                if(mainTeam != null)
                {
                    teamId = mainTeam.getId();
                    teamName = mainTeam.getName();
                }
            }

            if(projectMember != null)
            {
                isOwner = projectMember.getRole().toString()=="OWNER";
            }

            return new ProjectBasicInfo(
                    Math.toIntExact(project.getId()),
                    project.getName(),
                    project.getDescription(),
                    isOwner,
                    teamId,
                    teamName
            );
        }

        public static ProjectMemberInfo map(ProjectMember projectMember) {
            UserBasicInfo userBasicInfo = new UserBasicInfo(projectMember.getId().getMemberId(),
                    projectMember.getUser().getName(),
                    projectMember.getUser().getEmail());
            return new ProjectMemberInfo(userBasicInfo, projectMember.getRole().name());
        }

        public static Project map(ProjectCreateRequest project, User owner, Set<Team> teamsToAdd, Set<User> usersToAdd) {
            Project createdProject = new Project(
                    owner,
                    project.getName(),
                    project.getCategory(),
                    project.getDescription()
            );

            if (teamsToAdd != null) {
                for (Team team : teamsToAdd) {
                    createdProject.addTeam(team);
                }
            }

            if (usersToAdd != null) {
                for (User user : usersToAdd) {
                    createdProject.addProjectMember(user);
                }
            }

            System.out.println(createdProject.getOwner());
            System.out.println(createdProject.getTeams());

            return createdProject;
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
