package com.example.springusermanager.controller;

import com.example.springusermanager.model.User;
import com.example.springusermanager.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testCreateUser() {
        // Given
        User user = new User(
                "test@example.com",
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "Address",
                "1234567890"
        );

        // When
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.createUser(user);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void testPartiallyUpdateUser() {
        // Given
        String email = "test@example.com";
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstName", "John");
        User updatedUser = new User(
                email,
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "Address",
                "1234567890"
        );

        // When
        when(userService.partiallyUpdateUser(email, updates)).thenReturn(updatedUser);

        ResponseEntity<User> responseEntity = userController.partiallyUpdateUser(email, updates);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUser, responseEntity.getBody());
    }

    @Test
    void testUpdateUser() {
        // Given
        String email = "test@example.com";
        User user = new User(
                email,
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "Address",
                "1234567890"
        );

        // When
        when(userService.updateUser(email, user)).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.updateUser(email, user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void testDeleteUser() {
        // Given
        String email = "test@example.com";

        // When
        ResponseEntity<Void> responseEntity = userController.deleteUser(email);

        // Then
        verify(userService, times(1)).deleteUser(email);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testSearchUsersByBirthDateRange() {
        // Given
        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(2000, 12, 31);
        List<User> userList = List.of(
                new User(
                        "test1@example.com",
                        "John",
                        "Doe",
                        LocalDate.of(1995, 5, 5),
                        "Address1",
                        "1234567890"
                ),
                new User(
                        "test2@example.com",
                        "Jane",
                        "Doe",
                        LocalDate.of(1998, 7, 15),
                        "Address2",
                        "1234567890"
                )
        );

        // When
        when(userService.searchUsersByBirthDateRange(from, to)).thenReturn(userList);

        ResponseEntity<List<User>> responseEntity = userController.searchUsersByBirthDateRange(from, to);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userList, responseEntity.getBody());
    }
}