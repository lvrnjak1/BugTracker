package ba.project.bugtracker.controllers;

import ba.project.bugtracker.model.User;
import ba.project.bugtracker.services.ProjectService;
import ba.project.bugtracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/user/{userId}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/projects")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getProjects(Principal principal){
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(user.getProjects());
    }
}
