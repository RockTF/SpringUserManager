package com.example.springusermanager.service;

import com.example.springusermanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.now().minusYears(25));
        user.setAddress("123 Fake Street");
        user.setPhoneNumber("555-1234");
    }

    @Test
    void createUserValidUserReturnsUser() {
        User result = userService.createUser(user);
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void createUserUnderageUserThrowsException() {
        User underageUser = new User();
        underageUser.setBirthDate(LocalDate.now().minusYears(16));
        ReflectionTestUtils.setField(userService, "minimumAge", 18);
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(underageUser));
    }

    @Test
    void updateUserExistingUserUpdatesSuccessfully() {
        userService.createUser(user);
        user.setFirstName("Jane");
        User updatedUser = userService.updateUser(user.getEmail(), user);
        assertNotNull(updatedUser);
        assertEquals("Jane", updatedUser.getFirstName());
    }

    @Test
    void updateUserNonExistingUserReturnsNull() {
        User nonExisting = userService.updateUser("nonexistent@example.com", user);
        assertNull(nonExisting);
    }

    @Test
    void deleteUserExistingUserDeletesSuccessfully() {
        userService.createUser(user);
        userService.deleteUser(user.getEmail());
        assertTrue(userService.searchUsersByBirthDateRange(LocalDate.of(1990, 1, 1), LocalDate.now()).isEmpty());
    }

    @Test
    void searchUsersByBirthDateRangeValidRangeReturnsUsers() {
        userService.createUser(user);
        List<User> results = userService.searchUsersByBirthDateRange(user.getBirthDate().minusDays(1), user.getBirthDate().plusDays(1));
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void searchUsersByBirthDateRangeInvalidRangeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userService.searchUsersByBirthDateRange(LocalDate.now(), LocalDate.now().minusDays(1)));
    }
}
