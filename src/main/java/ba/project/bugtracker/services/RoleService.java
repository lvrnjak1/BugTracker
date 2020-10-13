package ba.project.bugtracker.services;

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
        //roleRepository.findAll().forEach(System.out::println);
        return roleRepository.findByRole(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
