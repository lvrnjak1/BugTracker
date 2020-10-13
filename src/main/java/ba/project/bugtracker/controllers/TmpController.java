package ba.project.bugtracker.controllers;

import ba.project.bugtracker.model.User;
import ba.project.bugtracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/tmp")
@RequiredArgsConstructor
public class TmpController {
    private final UserService userService;

    @GetMapping("/test")
    @Secured("ROLE_USER")
    public String rolesTest(Principal principal){
        User user = userService.findByUsername(principal.getName());
        return "Hello " + user.getUsername();
    }
}
