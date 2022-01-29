package pl.edu.pw.app.api.service;

import pl.edu.pw.app.api.dto.projectDTO.AddFavoriteProject;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.userDTO.UserUpdateRequest;
import javassist.NotFoundException;
import pl.edu.pw.app.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface UserService {
    List<UserUpdateRequest> getAll();

    UserUpdateRequest add(UserUpdateRequest userUpdateRequest) throws SQLException;

    void deleteById(Long id) throws NotFoundException;

    UserUpdateRequest findById(Long id) throws NotFoundException;

    void updateById(Long id, UserUpdateRequest facultyDto) throws NotFoundException;

    String signUpUser(User user);

    int enableUser(String email);

    void addToFavorites(AddFavoriteProject project);

    void removeFromFavorites(Long projectId);

    Set<ProjectCompleteInfo> getAllFavorites();
}
