package ma.akenord.v1.authentification;

import lombok.RequiredArgsConstructor;
import ma.akenord.v1.entity.Role;
import ma.akenord.v1.entity.User;
import ma.akenord.v1.jwt.JwtService;
import ma.akenord.v1.repository.RoleRepository;
import ma.akenord.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        Optional<User> userExists = userRepository.findById(request.getUsername());
        // Check if a user with the same username already exists
        if (userExists.isPresent()) {
            // User with the same username already exists, send a response message
            return AuthenticationResponse.builder()
                    .message("User with the provided username already exists.")
                    .build();
        }

        Role role_user = new Role();
        Optional<Role> role_exist = roleRepository.findById("ROLE_USER");
        if (role_exist.isPresent()) {
            role_user = role_exist.get();
        }

        var user = User.builder()
                .username(request.getUsername())
                .first_name(request.getFirstName())
                .last_name(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone_number(request.getPhoneNumber())
                .enabled(request.isEnabled())
                .birth_date(request.getBirthDate())
                .created_at(LocalDateTime.now())
                .roles(Collections.singleton(role_user))
                .build();
        System.out.println(user);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findById(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
