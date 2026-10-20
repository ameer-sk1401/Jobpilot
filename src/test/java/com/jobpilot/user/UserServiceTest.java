package com.jobpilot.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService users;
    @Autowired PasswordEncoder encoder;

    @Test
    void registersNewUserWithHashedPassword() {
        User user = users.register("a@b.com", "password123");
        assertNotNull(user.getId());
        assertNotEquals("password123", user.getPasswordHash());
        assertTrue(encoder.matches("password123", user.getPasswordHash()));
    }

    @Test
    void duplicateEmailThrows() {
        users.register("a@b.com", "password123");
        assertThrows(DuplicateEmailException.class,
            () -> users.register("a@b.com", "password123"));
    }
}
