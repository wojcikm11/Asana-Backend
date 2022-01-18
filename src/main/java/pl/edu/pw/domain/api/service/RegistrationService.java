package pl.edu.pw.domain.api.service;

import pl.edu.pw.domain.api.dto.userDTO.UserCreateRequest;

public interface RegistrationService {

    String register (UserCreateRequest request);

    String confirmToken(String token);
}
