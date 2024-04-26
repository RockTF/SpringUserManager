package com.example.springusermanager.service;

import com.example.springusermanager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testCreateUser_ValidAge() {
        User user = new User(
                "valid@example.com",
                "Jane",
                "Doe",
                LocalDate.now().minusYears(20),
                "456 Real St",
                "123-4567"
        );
        assertDoesNotThrow(() -> userService.createUser(user));
    }

    @Test
    void testCreateUser_InvalidAge() {
        User user = new User(
                "invalid@example.com",
                "Jim",
                "Bean",
                LocalDate.now().minusYears(16),
                "789 Imaginary St",
                "890-1234"
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        assertEquals("User must be at least 18 years old.", exception.getMessage());
    }
}
