package com.jobpilot.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository users, PasswordEncoder passwordEncoder){
        this.users = users;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional 
    public User register(String email,String password){
        if(users.existsByEmail(email)){
            throw new DuplicateEmailException(email);
        }
        return users.save(new User(email,passwordEncoder.encode(password)));
    }
}
