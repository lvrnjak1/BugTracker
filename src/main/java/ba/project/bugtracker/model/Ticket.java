package ba.project.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "tickets")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends Entity{
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TicketType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TicketPriority priority;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Project project;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
