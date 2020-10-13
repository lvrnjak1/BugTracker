package ba.project.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Table(name = "projects")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Project extends Entity{
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User projectManager;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<User> developers = new HashSet<>();

    public Project(String name, User projectManager) {
        this.name = name;
        this.projectManager = projectManager;
    }
}
