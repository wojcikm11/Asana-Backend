package pl.edu.pw.domain.api.service;

import pl.edu.pw.domain.api.dto.UserUpdateRequest;
import javassist.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<UserUpdateRequest> getAll();

    UserUpdateRequest add(UserUpdateRequest userUpdateRequest) throws SQLException;

    void deleteById(Long id) throws NotFoundException;

    UserUpdateRequest findById(Long id) throws NotFoundException;

    void updateById(Long id, UserUpdateRequest facultyDto) throws NotFoundException;
}
