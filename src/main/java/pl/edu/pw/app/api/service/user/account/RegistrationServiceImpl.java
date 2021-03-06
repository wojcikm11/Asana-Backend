package pl.edu.pw.app.api.service.user.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.api.dto.userDTO.UserCreateRequest;
import pl.edu.pw.app.api.service.user.UserService;
import pl.edu.pw.app.domain.user.User;
import pl.edu.pw.security.token.ConfirmationToken;
import pl.edu.pw.security.validator.EmailValidator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private EmailValidator emailValidator;
    private UserService userService;
    private ConfirmationTokenServiceImpl confirmationTokenService;
    private EmailSenderService emailSenderService;

    private final String registrationMessage ="Thank you for registering. Please click on the below link to activate your account:";
    private final String actionMsg = "Confirm your email";
    private final String linkMsg ="Activate now";


    @Override
    public String register(UserCreateRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("Email not valid");
        }

        String token = userService.signUpUser(
                new User(

                        request.getEmail(),
                        request.getPassword(),
                        request.getName()
                )
        );

        String link = "http://localhost:8080/api/registration/confirm?token="+token;
        emailSenderService.send(request.getEmail(),emailSenderService.buildEmail(request.getEmail(),link,registrationMessage,actionMsg,linkMsg));

        return token;
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(()->
                new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt =confirmationToken.getExpiredAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token has expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());


        return "confirmed";
    }



}
