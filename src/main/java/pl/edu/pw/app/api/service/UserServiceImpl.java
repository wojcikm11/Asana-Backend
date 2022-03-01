package pl.edu.pw.app.api.service;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.projectDTO.AddFavoriteProject;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;
import pl.edu.pw.app.api.dto.userDTO.UserUpdateRequest;
import pl.edu.pw.app.domain.Project;
import pl.edu.pw.app.repository.ProjectRepository;
import pl.edu.pw.app.repository.UserRepository;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.security.token.ConfirmationToken;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;



    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ConfirmationTokenServiceImpl confirmationTokenService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserBasicInfo> getAll() {
        return userRepository.findAll().stream().map(UserMapper::mapToBasicInfo).toList();
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User with email: "+email+" not found"));
    }

    public String signUpUser(User user){
       boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists ){
            throw new IllegalStateException("User with this email already exists: "+user.getEmail());
        }

        String psw = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(psw);


        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);


        return token;
    }

    @Override
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    @Override
    public void addToFavorites(AddFavoriteProject project) {
        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException("Could not find user with given id");
        });
        Project projectToAdd = projectRepository.findById(project.getProjectId()).orElseThrow();
        user.addToFavorites(projectToAdd);
        userRepository.save(user);
    }

    @Override
    public void removeFromFavorites(Long projectId) {
        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException("Could not find user with given id");
        });
        Project projectToRemove = projectRepository.findById(projectId).orElseThrow();
        user.removeFromFavorites(projectToRemove);
        userRepository.save(user);
    }

    @Override
    public Set<ProjectCompleteInfo> getAllFavorites() {
        User user = userRepository.findByEmail(UtilityService.getCurrentUser()).orElseThrow(()->{
            throw new IllegalArgumentException("Could not find user with given id");
        });
        return user.getFavoriteProjects().stream().map(ProjectService.ProjectMapper::map).collect(Collectors.toSet());
    }

    public static class UserMapper {
        public static UserUpdateRequest map(User user) {
            return new UserUpdateRequest(
                user.getEmail(),
                user.getPassword(),
                user.getName()
            );
        }

        public static User map(UserUpdateRequest userUpdateRequest) {
            return new User(
                userUpdateRequest.getEmail(),
                userUpdateRequest.getPassword(),
                userUpdateRequest.getName()
            );
        }

        public static UserBasicInfo mapToBasicInfo(User user) {
            return new UserBasicInfo(
                user.getId(),
                user.getName(),
                user.getEmail()
            );
        }
    }
}
