package ba.project.bugtracker.services;

import ba.project.bugtracker.exceptions.custom.EntityNotFoundException;
import ba.project.bugtracker.model.Role;
import ba.project.bugtracker.repositories.RoleRepository;
import ba.project.bugtracker.utility.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByRoleName(RoleName roleName){
        return roleRepository.findByRole(roleName)
                .orElseThrow(
                        () -> new EntityNotFoundException("Role " + roleName.name() + " doesn't exist")
                );
    }
}
