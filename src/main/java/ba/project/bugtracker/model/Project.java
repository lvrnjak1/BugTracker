package ba.project.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Table(name = "projects")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Project extends ba.project.bugtracker.model.Entity {
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String code;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User projectManager;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<User> developers = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Ticket> tickets = new HashSet<>();

    public Project(String name, User projectManager) {
        this.name = name;
        this.projectManager = projectManager;
        this.code = generateCode();
    }

    private String generateCode() {
        int length = 6;
        return RandomStringUtils.random(length, true, true);
    }

    public void addDeveloper(User developer) {
        developers.add(developer);
    }

    public void removeDeveloper(User developer) {
        developers.remove(developer);
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket){
        tickets.remove(ticket);
    }
}
