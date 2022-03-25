package pl.edu.pw.app.api.service.user.account;

import pl.edu.pw.app.api.dto.userDTO.UserCreateRequest;

public interface RegistrationService {

    String register (UserCreateRequest request);

    String confirmToken(String token);
}
