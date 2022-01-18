package pl.edu.pw.domain.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.domain.api.dto.userDTO.UserCreateRequest;
import pl.edu.pw.domain.api.service.ConfirmationTokenServiceImpl;
import pl.edu.pw.domain.api.service.RegistrationService;
import pl.edu.pw.domain.api.service.UserService;

import javax.servlet.Registration;

@AllArgsConstructor
@RestController
@RequestMapping(path="api/registration")
public class UserController {

    private UserService userService;
    private RegistrationService registrationService;


    @PostMapping
    public String register (@RequestBody UserCreateRequest request){
        return registrationService.register(request);

    }

    @GetMapping(path="confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
