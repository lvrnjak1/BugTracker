package ba.project.bugtracker.model;

import ba.project.bugtracker.utility.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Table(name = "roles")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role extends Entity{
    @Enumerated(value = EnumType.STRING)
    private RoleName role;
}
