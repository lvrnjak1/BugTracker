package ba.project.bugtracker.controllers;

import ba.project.bugtracker.exceptions.custom.EntityNotFoundException;
import ba.project.bugtracker.exceptions.custom.IllegalActionException;
import ba.project.bugtracker.model.Project;
import ba.project.bugtracker.model.Ticket;
import ba.project.bugtracker.model.User;
import ba.project.bugtracker.requests.TicketRequest;
import ba.project.bugtracker.services.ProjectService;
import ba.project.bugtracker.services.TicketService;
import ba.project.bugtracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;
    private final ProjectService projectService;

    //add ticket (user)
    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<?> addTicket(@RequestParam(name = "code") String projectCode,
                                       @RequestBody @Valid TicketRequest ticketRequest,
                                       Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findByCode(projectCode);
        Ticket ticket = ticketService.getTicketFromRequest(ticketRequest, user, project);
        project.addTicket(ticket);
        ticketService.save(ticket);
        projectService.save(project);
        return ResponseEntity.ok(ticket);
    }

    //add ticket (developer, manager)
    @PostMapping("/{projectId}")
    @Secured("ROLE_DEVELOPER")
    public ResponseEntity<?> addTicketAsDeveloper(@PathVariable Long projectId,
                                                  @RequestBody @Valid TicketRequest ticketRequest,
                                       Principal principal){
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(projectId);
        Ticket ticket = ticketService.getTicketFromRequest(ticketRequest, user, project);
        project.addTicket(ticket);
        ticketService.save(ticket);
        projectService.save(project);
        return ResponseEntity.ok(ticket);
    }

    //edit ticket

    //delete ticket


    //assign ticket to developer
    @PutMapping("/{ticketId}/assign")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> assignTicketToDeveloper(@PathVariable Long ticketId,
                                                     @RequestParam(name = "developerId") Long developerId,
                                                     Principal principal){
        User user = userService.findByUsername(principal.getName());
        User developer = userService.findById(developerId);
        Ticket ticket = ticketService.findById(ticketId);
        Project project = ticket.getProject();

        if(!project.getProjectManager().equals(user)){
            throw new EntityNotFoundException("Project with id " + ticket.getProject().getId() + " doesn't exist");
        }

        if(!developer.getProjectsWorkingOn().contains(project)){
            throw new IllegalActionException("This developer isn't assigned to this project");
        }

        ticket.setDeveloper(developer);
        developer.addTicketsWorkingOn(ticket);

        ticketService.save(ticket);
        userService.save(developer);

        return ResponseEntity.ok().build();
    }


    //remove developer from ticket
    @PutMapping("/{ticketId}/remove")
    @Secured("ROLE_MANAGER")
    public ResponseEntity<?> removeDeveloperFromTicket(@PathVariable Long ticketId,
                                                     @RequestParam(name = "developerId") Long developerId,
                                                     Principal principal){
        User user = userService.findByUsername(principal.getName());
        User developer = userService.findById(developerId);
        Ticket ticket = ticketService.findById(ticketId);
        Project project = ticket.getProject();

        if(!project.getProjectManager().equals(user)){
            throw new EntityNotFoundException("Project with id " + ticket.getProject().getId() + " doesn't exist");
        }

        if(!ticket.getDeveloper().equals(developer)){
            throw new IllegalActionException("This developer is not assigned to this ticket!");
        }

        developer.removeTicketsWorkingOn(ticket);
        ticket.setDeveloper(null);
        userService.save(developer);
        ticketService.save(ticket);

        return ResponseEntity.ok().build();
    }
}
