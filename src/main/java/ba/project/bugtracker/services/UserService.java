package ba.project.bugtracker.services;

import ba.project.bugtracker.exceptions.custom.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("User " + username + " doesn't exist!"));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist!"));
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
