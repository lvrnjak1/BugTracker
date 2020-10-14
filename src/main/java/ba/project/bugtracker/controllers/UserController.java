package ba.project.bugtracker.controllers;

import ba.project.bugtracker.model.Project;
import ba.project.bugtracker.model.User;
import ba.project.bugtracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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

    @DeleteMapping("/user")
    @Secured("ROLE_USER")
    public ResponseEntity<?> deleteAccount(Principal principal){
        User user = userService.findByUsername(principal.getName());
//        user.setProjectsWorkingOn(new HashSet<>());
//        User saved = userService.save(user);
        for (Project p : user.getProjectsWorkingOn()){
            p.getDevelopers().remove(user);
        }
        userService.delete(user);
        return ResponseEntity.ok().build();
    }
}