package com.example.springusermanager.service;

import com.example.springusermanager.model.User;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IUserService {
    User createUser(@Valid User user);

    User partiallyUpdateUser(String email, Map<String, Object> updates);

    User updateUser(String email, User updatedUser);

    void deleteUser(String email);

    List<User> searchUsersByBirthDateRange(LocalDate from, LocalDate to);
}
