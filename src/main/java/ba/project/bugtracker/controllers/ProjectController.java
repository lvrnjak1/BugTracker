package ba.project.bugtracker.controllers;

import ba.project.bugtracker.exceptions.custom.EntityNotFoundException;
import ba.project.bugtracker.exceptions.custom.IllegalActionException;
import ba.project.bugtracker.model.User;
import ba.project.bugtracker.model.enums.RoleName;
import ba.project.bugtracker.requests.ProjectRequest;
import ba.project.bugtracker.responses.ApiResponse;
import ba.project.bugtracker.services.ProjectService;
import ba.project.bugtracker.services.RoleService;
import ba.project.bugtracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ba.project.bugtracker.model.Project;

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
        project.addDeveloper(user);
        projectService.save(project);

        user.addProjects(project);
        user.addRole(roleService.findByRoleName(RoleName.ROLE_MANAGER));
        user.addRole(roleService.findByRoleName(RoleName.ROLE_DEVELOPER));
        userService.save(user);

        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectId}")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId,
                                           Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);

        if(!project.getProjectManager().equals(user)){
            //throw this error so that the user doesn't know
            // if this id even exists within other projects
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        if(user.getProjects().size() == 1){
            System.out.println("Here");
            user.removeRole(roleService.findByRoleName(RoleName.ROLE_MANAGER));
            userService.save(user);
        }

        projectService.delete(project);
        return ResponseEntity.ok(new ApiResponse("Project " + project.getName() + " successfully deleted!"));
    }

    @PutMapping("/{projectId}")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> editProject(@PathVariable Long projectId,
                                         @RequestBody ProjectRequest projectRequest,
                                         Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);

        if(!project.getProjectManager().equals(user)){
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        project.setName(projectRequest.getName());
        projectService.save(project);

        return ResponseEntity.ok(project);
    }

    @GetMapping("/{projectId}")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectId,
                                         Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);

        if(!project.getProjectManager().equals(user)){
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        return ResponseEntity.ok(project);
    }

    @PutMapping("/{projectId}/developer")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> assignDeveloperToProject(@PathVariable Long projectId,
                                                      @RequestParam(name = "id") Long developerId,
                                                      Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);

        if(!project.getProjectManager().equals(user)){
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        User developer = userService.findById(developerId);
        developer.addProjectsWorkingOn(project);
        developer.addRole(roleService.findByRoleName(RoleName.ROLE_DEVELOPER));
        projectService.save(project);
        userService.save(developer);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/developer")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> removeDeveloperFromProject(@PathVariable Long projectId,
                                                        @RequestParam(name = "id") Long developerId,
                                                        Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);

        if(!project.getProjectManager().equals(user)){
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        User developer = userService.findById(developerId);

        if(developer.getId().equals(user.getId())){
            throw new IllegalActionException("You can't remove yourself from your project!");
        }

        developer.removeProjectWorkingOn(project);

        if(developer.getProjectsWorkingOn().isEmpty()){
            developer.removeRole(roleService.findByRoleName(RoleName.ROLE_DEVELOPER));
        }

        projectService.save(project);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}/developers")
    @Secured({"ROLE_MANAGER", "ROLE_DEVELOPER"})
    public ResponseEntity<?> getAllDevelopersForProject(@PathVariable Long projectId,
                                                        Principal principal){

        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);

        if(!project.getProjectManager().equals(user)){
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        return ResponseEntity.ok(project.getDevelopers());
    }

    @GetMapping("/{projectId}/tickets")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> getAllTickets(@PathVariable Long projectId, Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);

        if(!project.getProjectManager().equals(user)){
            throw new EntityNotFoundException("Project with id " + projectId + " doesn't exist");
        }

        return ResponseEntity.ok(project.getTickets());
    }
}
