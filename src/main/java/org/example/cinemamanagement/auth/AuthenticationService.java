package org.example.cinemamanagement.auth;

import lombok.RequiredArgsConstructor;
import org.example.cinemamanagement.common.Role;
import org.example.cinemamanagement.service.JwtService;
import org.example.cinemamanagement.mapper.AccountMapper;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        userRepository.findUserByEmail(request.getEmail()).ifPresent(user -> {
            throw new RuntimeException("User with email " + request.getEmail() + " already exists");
        });

        var user = Account.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .user(AccountMapper.toDTO(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid email or password.");
        }

        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .user(AccountMapper.toDTO(user))
                .build();

    }
}
