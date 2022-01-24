package pl.edu.pw.app.api.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.app.repository.ConfirmationTokenRepository;
import pl.edu.pw.security.token.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl {


    private ConfirmationTokenRepository confirmationTokenRepository;


    public void saveConfirmationToken(ConfirmationToken token){
      confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
