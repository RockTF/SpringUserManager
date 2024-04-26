package com.example.springusermanager.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {

    @Test
    void testUserModel() {

        User user = new User(
                "user@example.com",
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "123 Fake St",
                "555-5555"
        );

        assertAll("user",
                () -> assertEquals("user@example.com", user.getEmail()),
                () -> assertEquals("John", user.getFirstName()),
                () -> assertEquals("Doe", user.getLastName()),
                () -> assertEquals(LocalDate.of(1990, 1, 1), user.getBirthDate()),
                () -> assertEquals("123 Fake St", user.getAddress()),
                () -> assertEquals("555-5555", user.getPhoneNumber())
        );
    }
}
