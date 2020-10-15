package ba.project.bugtracker.services;

import ba.project.bugtracker.exceptions.custom.EntityNotFoundException;
import ba.project.bugtracker.model.*;
import ba.project.bugtracker.model.enums.TicketPriorityName;
import ba.project.bugtracker.model.enums.TicketTypeName;
import ba.project.bugtracker.repositories.TicketPriorityRepository;
import ba.project.bugtracker.repositories.TicketRepository;
import ba.project.bugtracker.repositories.TicketTypeRepository;
import ba.project.bugtracker.requests.TicketRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketPriorityRepository ticketPriorityRepository;

    public Ticket getTicketFromRequest(TicketRequest ticketRequest, User author, Project project) {
        TicketTypeName typeName;
        try {
            typeName = TicketTypeName.valueOf(ticketRequest.getType());
        }catch (IllegalArgumentException exception){
            throw new EntityNotFoundException("Invalid ticket type");
        }

        TicketPriorityName priorityName;
        try {
            priorityName = TicketPriorityName.valueOf(ticketRequest.getPriority());
        }catch (IllegalArgumentException exception){
            throw new EntityNotFoundException("Invalid ticket priority");
        }

        TicketType ticketType = ticketTypeRepository.findByType(typeName)
                .orElseThrow(() -> new EntityNotFoundException("Invalid ticket type"));
        TicketPriority ticketPriority = ticketPriorityRepository.findByPriority(priorityName)
                .orElseThrow(() -> new EntityNotFoundException("Invalid ticket priority"));

        return new Ticket(ticketRequest.getTitle(), ticketRequest.getDescription(),
                new Timestamp(ticketRequest.getTimestamp()), author, ticketType, ticketPriority, project);
    }

    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
