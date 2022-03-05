package pl.edu.pw.app.api.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import pl.edu.pw.app.api.dto.projectDTO.AddFavoriteProject;
import pl.edu.pw.app.api.dto.projectDTO.ProjectCompleteInfo;
import pl.edu.pw.app.api.dto.userDTO.ResetPasswordRequest;
import pl.edu.pw.app.api.dto.userDTO.UserBasicInfo;
import pl.edu.pw.app.api.dto.userDTO.UserCreateRequest;
import pl.edu.pw.app.api.service.PasswordResetService;
import pl.edu.pw.app.api.service.RegistrationService;
import pl.edu.pw.app.api.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(path="api")
@CrossOrigin("*")
public class UserController {

    private UserService userService;
    private RegistrationService registrationService;
    private PasswordResetService passwordResetService;


    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateRequest request){
       registrationService.register(request);
       return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping(path="/registration/confirm")
    public void confirm(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        response.setStatus(302);
        try {
            registrationService.confirmToken(token);

//        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http:localhost:3000/confirm")).build();
//        return new ModelAndView("redirect:" + "http:localhost:3000/confirm");

            String externalUrl = "http://localhost:3000/confirm";
            response.setHeader("Location", externalUrl);
//            response.setStatus(200);
        }catch(Exception e){
            String externalUrl = "http://localhost:3000/confirm?error=" + e.getMessage();
//            response.sendError(400, e.getMessage());
            response.setHeader("Location", externalUrl);
        }
    }

    @GetMapping("/user/all")
    public List<UserBasicInfo> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    public UserBasicInfo getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/registration/whoami")
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


    @PostMapping("password/forgot")
    public ResponseEntity<?> sendPasswordResetLink(@RequestParam String email){
        passwordResetService.sendToken(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("password/change")
    public String returnResetToken(@RequestParam String token){
        return "reset password"+token;
    }

    @PostMapping("password/change")
    public ResponseEntity<?> setNewPassword(@Valid @RequestBody ResetPasswordRequest
                                                     newPassword){
        passwordResetService.setNewPassword(newPassword);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
