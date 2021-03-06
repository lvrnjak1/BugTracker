package ba.project.bugtracker.services;

import ba.project.bugtracker.auth.JwtProvider;
import ba.project.bugtracker.exceptions.custom.EmailAlreadyInUse;
import ba.project.bugtracker.exceptions.custom.InvalidCredentialsException;
import ba.project.bugtracker.exceptions.custom.UsernameNotAvailableException;
import ba.project.bugtracker.model.User;
import ba.project.bugtracker.model.enums.RoleName;
import ba.project.bugtracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Username " + username + " not found!"));
    }

    public String authenticate(String username, String password){
        UserDetails userDetails = loadUserByUsername(username);
        boolean correctPassword = passwordEncoder.matches(password, userDetails.getPassword());
        if(!correctPassword){
            throw new InvalidCredentialsException("Bad credentials");
        }
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtProvider.generateToken(userDetails);
    }

    public User register(User user){
        checkUsernameAvailability(user.getUsername());
        checkEmailAvailability(user.getEmail());
        user.getRoles().add(roleService.findByRoleName(RoleName.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    private void checkUsernameAvailability(String username){
        if(userRepository.findByUsername(username).isPresent()){
            throw new UsernameNotAvailableException("Username " + username + " is not available");
        }
    }
    private void checkEmailAvailability(String email){
        if(userRepository.findByEmail(email).isPresent()){
            throw new EmailAlreadyInUse("Email " + email + " is already in use");
        }
    }
}
