package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.app.api.dto.projectDTO.AddFavoriteProject;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.userDTO.UserCreateRequest;
import pl.edu.pw.app.api.service.RegistrationService;
import pl.edu.pw.app.api.service.UserService;

import javax.validation.Valid;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(path="api")
public class UserController {

    private UserService userService;
    private RegistrationService registrationService;


    @PostMapping("/registration")
    public String register(@RequestBody UserCreateRequest request){
        return registrationService.register(request);

    }

    @GetMapping(path="/registration/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }


    @GetMapping("/whoami")
    public String elo(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/user/favorites")
    public Set<ProjectCompleteInfo> getAllFavorites() {
        return userService.getAllFavorites();
    }

    @PostMapping("/user/favorites/add")
    public ResponseEntity<?> addToFavorites(@Valid @RequestBody AddFavoriteProject project) {
        userService.addToFavorites(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/user/favorites/delete/{id}")
    public ResponseEntity<?> deleteFromFavorites(@PathVariable Long id) {
        userService.removeFromFavorites(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
