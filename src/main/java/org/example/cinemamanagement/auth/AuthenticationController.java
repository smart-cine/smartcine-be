package org.example.cinemamanagement.auth;

import lombok.RequiredArgsConstructor;
import org.example.cinemamanagement.payload.response.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse authRes = service.register(request);
        DataResponse<AuthenticationResponse> response = DataResponse.<AuthenticationResponse>builder()
                .message("Register successfully")
                .success(true)
                .data(authRes)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse authenRes = service.authenticate(request);
        DataResponse<AuthenticationResponse> response = DataResponse.<AuthenticationResponse>builder()
                .message("Login successfully")
                .success(true)
                .data(authenRes)
                .build();
        return ResponseEntity.ok(response);
    }
}
