package com.jobpilot.auth;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthService auth;

    public AuthController(AuthService auth){
        this.auth=auth;

    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){
        return LoginResponse.bearer(auth.login(request.email(),request.password()));
    }
}
