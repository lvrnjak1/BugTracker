package ba.project.bugtracker.model;

import ba.project.bugtracker.model.enums.TicketPriorityName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "ticket_priorities")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketPriority extends Entity{
    @Enumerated(value = EnumType.STRING)
    private TicketPriorityName priority;
}
