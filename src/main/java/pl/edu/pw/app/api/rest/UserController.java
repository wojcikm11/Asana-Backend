package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.userDTO.UserCreateRequest;
import pl.edu.pw.app.api.service.RegistrationService;
import pl.edu.pw.app.api.service.UserService;

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


    @GetMapping("whoami")
    public String elo(){
        return  SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
