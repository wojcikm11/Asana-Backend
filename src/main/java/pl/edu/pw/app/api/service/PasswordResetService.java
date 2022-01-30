package pl.edu.pw.app.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.domain.User;
import pl.edu.pw.app.repository.PasswordResetRepository;
import pl.edu.pw.app.repository.UserRepository;
import pl.edu.pw.security.token.PasswordResetToken;
import pl.edu.pw.security.validator.EmailValidator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordResetService {

    private PasswordResetRepository passwordResetRepository;
    private EmailValidator emailValidator;
    private UserRepository userRepository;
    private EmailSenderService emailSenderService;
    private final String USER_NOT_FOUND_EXCEPTION = "User with given email address does not exist";


    @Transactional
    public void sendToken(String email){
        boolean isValidEmail = emailValidator.test(email);
        if(!isValidEmail){
            throw new IllegalStateException("Email not valid");
        }

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new IllegalStateException(USER_NOT_FOUND_EXCEPTION)
        );

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, user, LocalDateTime.now().plusMinutes(15));
        passwordResetRepository.save(myToken);

        String link = "http://localhost:8080/api/password/change?token="+token;
        emailSenderService.send(email,emailSenderService.buildEmail(email,link));



    }
}
