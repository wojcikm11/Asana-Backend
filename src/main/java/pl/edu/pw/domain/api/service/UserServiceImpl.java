package pl.edu.pw.domain.api.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.domain.api.dto.UserUpdateRequest;
import pl.edu.pw.domain.repository.UserRepository;
import pl.edu.pw.domain.user.User;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserUpdateRequest> getAll() {
        return userRepository.findAll().stream().map(UserMapper::map).toList();
    }

    @Override
    public UserUpdateRequest add(UserUpdateRequest userUpdateRequest) throws SQLException {
        User savedUser = userRepository.save(UserMapper.map(userUpdateRequest));
        return UserMapper.map(savedUser);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User with id " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserUpdateRequest findById(Long id) throws NotFoundException {
        User foundUser =  userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id " + id + " does not exist.")
        );
        return UserMapper.map(foundUser);
    }

    @Override
    public void updateById(Long id, UserUpdateRequest userUpdateRequest) throws NotFoundException {
        User user =  userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id " + id + " does not exist.")
        );
        user.setEmail(userUpdateRequest.getEmail());
        user.setName(userUpdateRequest.getName());
        user.setPassword(userUpdateRequest.getPassword());
        userRepository.save(user);
    }

    private static class UserMapper {
        private static UserUpdateRequest map(User user) {
            return new UserUpdateRequest(
                user.getEmail(),
                user.getPassword(),
                user.getName()
            );
        }

        private static User map(UserUpdateRequest userUpdateRequest) {
            return new User(
                userUpdateRequest.getEmail(),
                userUpdateRequest.getPassword(),
                userUpdateRequest.getName()
            );
        }
    }
}
