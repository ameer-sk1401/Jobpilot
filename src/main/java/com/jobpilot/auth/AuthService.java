package com.jobpilot.auth;

import com.jobpilot.user.UserRepository;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service public class AuthService{
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder passwordEncoder, JwtService jwtService){
        this .users = users;
        this.passwordEncoder= passwordEncoder;
        this.jwtService=jwtService;
    }

    public String login(String email,String password){
        var user=users.findByEmail(email)
        .filter(u-> passwordEncoder.matches(password,u.getPasswordHash()))
        .orElseThrow(InvalidCredentialsException::new);
        return jwtService.generateToken(user.getEmail());
    }
}