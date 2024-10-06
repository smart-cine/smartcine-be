package org.example.cinemamanagement.auth;

import lombok.RequiredArgsConstructor;
import org.example.cinemamanagement.common.Role;
import org.example.cinemamanagement.service.JwtService;
import org.example.cinemamanagement.model.Account;
import org.example.cinemamanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        accountRepository.findUserByEmail(request.getEmail()).ifPresent(user -> {
            throw new RuntimeException("User with email " + request.getEmail() + " already exists");
        });

        var user = Account.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .avatarURL(request.getAvartarURL())
                .build();

        accountRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .name( user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .avartarURL(user.getAvatarURL())
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

        Account user = accountRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .name( user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .avartarURL(user.getAvatarURL())
                .build();

    }
}
