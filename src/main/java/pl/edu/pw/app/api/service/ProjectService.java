package pl.edu.pw.app.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.AddProjectMember;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCreateRequest;
import pl.edu.pw.app.api.dto.projectDTO.ProjectUpdateRequest;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService implements IProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(ProjectCreateRequest project) {
        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException("User with given id does not exist");
        });
        projectRepository.save(map(project, user));
    }

    @Override
    public ProjectCompleteInfo get(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(this::map).orElseThrow();
    }

    @Override
    public List<ProjectCompleteInfo> getUserProjects(Long userId) {
        return projectRepository.findAll().stream()
                .filter(project -> project.getOwnerProjectMember().getUser().getId().equals(userId))
                .map(this::map).toList();
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
        project.addTeamMember(userToAdd);
    }

    @Override
    public void removeProjectMember(Long projectId, Long userId) {
        User member = userRepository.findById(userId).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();
        project.removeTeamMember(member);
    }

    private ProjectCompleteInfo map(Project project) {
        return new ProjectCompleteInfo(
                project.getId(),
                project.getName(),
                project.getCategory(),
                project.getDescription(),
                project.getOwnerProjectMember().getUser().getName()
        );
    }

    private Project map(ProjectCreateRequest project, User owner) {
        return new Project(
            owner,
            project.getName(),
            project.getCategory(),
            project.getDescription()
        );
    }
}
