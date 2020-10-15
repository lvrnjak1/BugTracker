package ba.project.bugtracker.controllers;

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
    //remove developer from ticket
}
