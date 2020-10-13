package ba.project.bugtracker.controllers;

import ba.project.bugtracker.exceptions.EmailAlreadyInUse;
import ba.project.bugtracker.exceptions.UsernameNotAvailableException;
import ba.project.bugtracker.model.User;
import ba.project.bugtracker.requests.LoginRequest;
import ba.project.bugtracker.requests.RegisterRequest;
import ba.project.bugtracker.responses.LoginResponse;
import ba.project.bugtracker.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        String jwtToken = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        User user = authService.loadUserByUsername(loginRequest.getUsername());
        return ResponseEntity.ok(new LoginResponse(user.getId(), user.getUsername(), jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws EmailAlreadyInUse, UsernameNotAvailableException {
        authService.register(new User(registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getEmail()));
        return ResponseEntity.ok("User " + registerRequest.getUsername() + " created!");
    }
}
