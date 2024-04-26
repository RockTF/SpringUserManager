package com.example.springusermanager.service;

import com.example.springusermanager.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Value("${user.minimum-age}")
    private int minimumAge;
    private final List<User> users = new ArrayList<>();

    public User createUser(@Valid User user) {
        LocalDate now = LocalDate.now();
        if (Period.between(user.getBirthDate(), now).getYears() < minimumAge) {
            throw new IllegalArgumentException("User must be at least " + minimumAge + " years old.");
        }
        users.add(user);
        return user;
    }

    public User updateUser(String email, User updatedUser) {
        return findUserByEmail(email).map(user -> {
            user.setFirstName(Optional.ofNullable(updatedUser.getFirstName()).orElse(user.getFirstName()));
            user.setLastName(Optional.ofNullable(updatedUser.getLastName()).orElse(user.getLastName()));
            user.setBirthDate(Optional.ofNullable(updatedUser.getBirthDate()).orElse(user.getBirthDate()));
            user.setAddress(Optional.ofNullable(updatedUser.getAddress()).orElse(user.getAddress()));
            user.setPhoneNumber(Optional.ofNullable(updatedUser.getPhoneNumber()).orElse(user.getPhoneNumber()));
            return user;
        }).orElse(null);
    }

    public void deleteUser(String email) {
        users.removeIf(user -> user.getEmail().equals(email));
    }

    public List<User> searchUsersByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From date must be before To date.");
        }
        return users.stream()
                .filter(user -> !user.getBirthDate().isBefore(from) && !user.getBirthDate().isAfter(to))
                .collect(Collectors.toList());
    }

    private Optional<User> findUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }
}
