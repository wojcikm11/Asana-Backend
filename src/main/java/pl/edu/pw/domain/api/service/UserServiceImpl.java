package pl.edu.pw.domain.api.service;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.domain.api.dto.userDTO.UserUpdateRequest;
import pl.edu.pw.domain.repository.ConfirmationTokenRepository;
import pl.edu.pw.domain.repository.UserRepository;
import pl.edu.pw.domain.user.User;
import pl.edu.pw.security.token.ConfirmationToken;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ConfirmationTokenServiceImpl confirmationTokenService;

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User with email: "+email+" not found"));
    }

    public String signUpUser(User user){
       boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        System.out.println("\n"+userExists+"\n");
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
