package pl.edu.pw.app.api.service;

import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.AddProjectMember;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCreateRequest;
import pl.edu.pw.app.api.dto.projectDTO.ProjectUpdateRequest;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface IProjectService {
    void create(ProjectCreateRequest project);
    ProjectCompleteInfo get(Long id);
    List<ProjectCompleteInfo> getUserProjects(Long userId);
    void update(Long projectId, ProjectUpdateRequest project);
    void delete(Long id);
    void addUserToProject(AddProjectMember addProjectMember);
    void removeProjectMember(Long projectId, Long userId);
}
