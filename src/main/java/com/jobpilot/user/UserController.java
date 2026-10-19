package com.jobpilot.user;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/users")
public class UserController {
    private final UserService users;
    public UserController(UserService users){
        this.users=users;
    }

    @PostMapping 
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        User user=users.register(request.email(),request.password());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(user.getId()).toUri();
        
        
        return ResponseEntity.created(location).body(UserResponse.from(user));
        
    }
    
}
