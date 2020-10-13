package ba.project.bugtracker.repositories;

import ba.project.bugtracker.model.Role;
import ba.project.bugtracker.utility.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleName roleName);
}
