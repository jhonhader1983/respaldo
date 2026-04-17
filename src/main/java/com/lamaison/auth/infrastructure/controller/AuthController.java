package com.lamaison.auth.controller;

import com.lamaison.auth.dto.request.LoginRequest;
import com.lamaison.auth.dto.request.PasswordResetRequest;
import com.lamaison.auth.dto.request.RegisterRequest;
import com.lamaison.auth.dto.response.AuthResponse;
import com.lamaison.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public Mono<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/password-reset/request")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> requestReset(@Valid @RequestBody PasswordResetRequest request) {
        return authService.requestPasswordReset(request);
    }

    @PostMapping("/password-reset/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> confirmReset(@RequestParam String token,
                                   @RequestParam String newPassword) {
        return authService.resetPassword(token, newPassword);
    }
}