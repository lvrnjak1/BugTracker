package ba.project.bugtracker.controllers;

import ba.project.bugtracker.exceptions.custom.UnauthorizedException;
import ba.project.bugtracker.model.Project;
import ba.project.bugtracker.model.User;
import ba.project.bugtracker.requests.ProjectRequest;
import ba.project.bugtracker.responses.ApiResponse;
import ba.project.bugtracker.responses.ProjectResponse;
import ba.project.bugtracker.services.ProjectService;
import ba.project.bugtracker.services.RoleService;
import ba.project.bugtracker.services.UserService;
import ba.project.bugtracker.utility.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {
    private final UserService userService;
    private final ProjectService projectService;
    private final RoleService roleService;

    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectRequest projectRequest,
                                           Principal principal){
        User user = userService.findByUsername(principal.getName());

        Project project = projectService.projectFromRequest(projectRequest, user);
        projectService.save(project);

        user.addProjects(project);
        user.addRole(roleService.findByRoleName(RoleName.ROLE_MANAGER));
        userService.save(user);

        return ResponseEntity.ok(new ProjectResponse(project.getId(), project.getName()));
    }

    @DeleteMapping("/{projectId}")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId,
                                           Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);

        if(!project.getProjectManager().equals(user)){
            throw new UnauthorizedException("You are not authorized to do this!");
        }

        projectService.delete(project);

        if(user.getProjects().isEmpty()){
            System.out.println("Here");
            user.removeRole(roleService.findByRoleName(RoleName.ROLE_MANAGER));
            userService.save(user);
        }
        return ResponseEntity.ok(new ApiResponse("Project " + project.getName() + " successfully deleted!"));
    }
}
