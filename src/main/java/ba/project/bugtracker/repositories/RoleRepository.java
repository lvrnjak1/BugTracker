package ba.project.bugtracker.repositories;

import ba.project.bugtracker.model.Role;
import ba.project.bugtracker.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleName roleName);
}
