package com.example.springusermanager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTests {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

    @BeforeEach
    void setUp() {
        email = "test@example.com";
        firstName = "John";
        lastName = "Doe";
        birthDate = LocalDate.of(1990, 1, 1);
        address = "123 Main St";
        phoneNumber = "123-456-7890";
    }

    @Test
    void testUserBuilder() {
        // When
        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();

        // Then
        assertAll("user",
                () -> assertNotNull(user),
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(firstName, user.getFirstName()),
                () -> assertEquals(lastName, user.getLastName()),
                () -> assertEquals(birthDate, user.getBirthDate()),
                () -> assertEquals(address, user.getAddress()),
                () -> assertEquals(phoneNumber, user.getPhoneNumber())
        );
    }

    @Test
    void testUserConstructor() {
        // When
        User user = new User(email, firstName, lastName, birthDate, address, phoneNumber);

        // Then
        assertAll("user",
                () -> assertNotNull(user),
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(firstName, user.getFirstName()),
                () -> assertEquals(lastName, user.getLastName()),
                () -> assertEquals(birthDate, user.getBirthDate()),
                () -> assertEquals(address, user.getAddress()),
                () -> assertEquals(phoneNumber, user.getPhoneNumber())
        );
    }

    @Test
    void testUserSetters() {
        // Given
        User user = new User();

        // When
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDate(birthDate);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);

        // Then
        assertAll("user",
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(firstName, user.getFirstName()),
                () -> assertEquals(lastName, user.getLastName()),
                () -> assertEquals(birthDate, user.getBirthDate()),
                () -> assertEquals(address, user.getAddress()),
                () -> assertEquals(phoneNumber, user.getPhoneNumber())
        );
    }
}
