package pl.edu.pw.app.api.service.user.account;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.userDTO.ResetPasswordRequest;
import pl.edu.pw.app.domain.user.User;
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
    private BCryptPasswordEncoder encoder;
    private final String USER_NOT_FOUND_EXCEPTION = "User with given email address does not exist";
    private final String resetMessage = "It seems like you've forgotten your password";
    private final String actionMsg = "Click the link below to reset password";
    private final String linkMsg ="Change password";


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
        emailSenderService.send(email,emailSenderService.buildEmail(email,link,resetMessage,actionMsg,linkMsg));



    }

    public void setNewPassword(ResetPasswordRequest newPassword){
       PasswordResetToken token  = passwordResetRepository.findByToken(newPassword.getToken()).orElseThrow(()->
                new IllegalStateException("Token not found"));
//       dodac spr. czy haslo bylo juz minione

        LocalDateTime expiredAt =token.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token has expired");
        }

        User user = token.getUser();
        user.setPassword(encoder.encode(newPassword.getPassword()));
        userRepository.save(user);
    }
}
