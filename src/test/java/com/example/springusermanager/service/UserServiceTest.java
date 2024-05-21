package com.example.springusermanager.service;

import com.example.springusermanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user;

    @Value("${user.minimum-age}")
    private int minimumAge;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .phoneNumber("123-456-7890")
                .build();
    }

    @Test
    void createUserValidUserReturnsUser() {
        // When
        User result = userService.createUser(user);

        // Then
        assertNotNull(result);

        // Then
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void createUserUnderageUserThrowsException() {
        // Given
        User underageUser = User.builder()
                .email("underage@example.com")
                .firstName("Underage")
                .lastName("User")
                .birthDate(LocalDate.now().minusYears(minimumAge - 1))
                .address("123 Main St")
                .phoneNumber("123-456-7890")
                .build();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(underageUser));
    }

    @Test
    void updateUserExistingUserUpdatesSuccessfully() {
        // Given
        userService.createUser(user);
        user.setFirstName("Jane");

        // When
        User updatedUser = userService.updateUser(user.getEmail(), user);

        // Then
        assertNotNull(updatedUser);
        assertEquals("Jane", updatedUser.getFirstName());
    }

    @Test
    void updateUserNonExistingUserReturnsNull() {
        // When
        User nonExisting = userService.updateUser("nonexistent@example.com", user);

        // Then
        assertNull(nonExisting);
    }

    @Test
    void deleteUserExistingUserDeletesSuccessfully() {
        // Given
        userService.createUser(user);

        // When
        userService.deleteUser(user.getEmail());

        // Then
        assertTrue(userService.searchUsersByBirthDateRange(LocalDate.of(1990, 1, 1), LocalDate.now()).isEmpty());
    }

    @Test
    void searchUsersByBirthDateRangeValidRangeReturnsUsers() {
        // Given
        userService.createUser(user);

        // When
        List<User> results = userService.searchUsersByBirthDateRange(user.getBirthDate().minusDays(1), user.getBirthDate().plusDays(1));

        // Then
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void searchUsersByBirthDateRangeInvalidRangeThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.searchUsersByBirthDateRange(LocalDate.now(), LocalDate.now().minusDays(1)));
    }
}
