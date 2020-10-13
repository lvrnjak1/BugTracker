package ba.project.bugtracker.services;

import ba.project.bugtracker.model.User;
import ba.project.bugtracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with this username doesn't exist"));
    }
}
