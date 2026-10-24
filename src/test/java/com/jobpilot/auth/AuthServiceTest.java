package com.jobpilot.auth;

import com.jobpilot.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired UserService userService;
    @Autowired AuthService authService;
    @Autowired JwtService jwtService;

    @Test
    void loginReturnsTokenForValidCredentials() {
        userService.register("a@b.com", "password123");
        String token = authService.login("a@b.com", "password123");
        assertEquals("a@b.com", jwtService.extractEmail(token));
    }

    @Test
    void wrongPasswordThrows() {
        userService.register("a@b.com", "password123");
        assertThrows(InvalidCredentialsException.class,
            () -> authService.login("a@b.com", "wrong-password"));
    }

    @Test
    void unknownEmailThrowsSameException() {
        assertThrows(InvalidCredentialsException.class,
            () -> authService.login("ghost@b.com", "password123"));
    }
}
