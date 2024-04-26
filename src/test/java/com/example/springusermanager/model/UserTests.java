package com.example.springusermanager.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {
    @Test
    void testUser() {
        // Given
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String address = "Address";
        String phoneNumber = "1234567890";

        User user = new User();

        // When
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDate(birthDate);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);

        // Then
        assertEquals(email, user.getEmail());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(address, user.getAddress());
        assertEquals(phoneNumber, user.getPhoneNumber());
    }
    @Test
    void testUserConstructor() {
        // Given
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String address = "Address";
        String phoneNumber = "1234567890";

        // When
        User user = new User(email, firstName, lastName, birthDate, address, phoneNumber);

        // Then
        assertEquals(email, user.getEmail());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(address, user.getAddress());
        assertEquals(phoneNumber, user.getPhoneNumber());
    }
}
