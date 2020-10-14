package ba.project.bugtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Table(name = "tickets")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket extends Entity{
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private Timestamp dateTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TicketType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TicketPriority priority;
}
