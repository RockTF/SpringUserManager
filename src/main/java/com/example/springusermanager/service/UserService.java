package com.example.springusermanager.service;

import com.example.springusermanager.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Value("${user.minimum-age}")
    private int minimumAge;
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public User createUser(@Valid User user) {
        validateUserAge(user);
        users.put(user.getEmail(), user);
        return user;
    }

    public User partiallyUpdateUser(String email, Map<String, Object> updates) {
        return findUserByEmail(email).map(user -> {
            updates.forEach((key, value) -> applyUpdate(user, key, value));
            return user;
        }).orElse(null);
    }

    public User updateUser(String email, User updatedUser) {
        return findUserByEmail(email).map(existingUser -> {
            updateField(existingUser::setFirstName, updatedUser.getFirstName(), existingUser.getFirstName());
            updateField(existingUser::setLastName, updatedUser.getLastName(), existingUser.getLastName());
            updateField(existingUser::setBirthDate, updatedUser.getBirthDate(), existingUser.getBirthDate());
            updateField(existingUser::setAddress, updatedUser.getAddress(), existingUser.getAddress());
            updateField(existingUser::setPhoneNumber, updatedUser.getPhoneNumber(), existingUser.getPhoneNumber());
            return existingUser;
        }).orElse(null);
    }

    public void deleteUser(String email) {users.remove(email);}

    public List<User> searchUsersByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From date must be before To date.");
        }
        return users.values().stream()
                .filter(user -> !user.getBirthDate().isBefore(from) && !user.getBirthDate().isAfter(to))
                .collect(Collectors.toList());
    }

    private void validateUserAge(User user) {
        LocalDate now = LocalDate.now();
        if (Period.between(user.getBirthDate(), now).getYears() < minimumAge) {
            throw new IllegalArgumentException("User must be at least " + minimumAge + " years old.");
        }
    }

    private void applyUpdate(User user, String key, Object value) {
        switch (key) {
            case "firstName": user.setFirstName((String) value); break;
            case "lastName": user.setLastName((String) value); break;
            case "birthDate": user.setBirthDate((LocalDate) value); break;
            case "address": user.setAddress((String) value); break;
            case "phoneNumber": user.setPhoneNumber((String) value); break;
            default: break;
        }
    }

    private Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    private <T> void updateField(Consumer<T> setter, T newValue, T oldValue) {
        setter.accept(Optional.ofNullable(newValue).orElse(oldValue));
    }
}
