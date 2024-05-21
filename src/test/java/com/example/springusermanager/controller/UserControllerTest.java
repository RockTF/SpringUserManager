package com.example.springusermanager.controller;

import com.example.springusermanager.model.User;
import com.example.springusermanager.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    private static final String BASE_URL = "/users";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 1, 1);
    private static final String ADDRESS = "123 Main St";
    private static final String PHONE_NUMBER = "123-456-7890";

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email(TEST_EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthDate(BIRTH_DATE)
                .address(ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .build();
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userToJson(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(TEST_EMAIL)));
    }

    @Test
    void testPartiallyUpdateUser() throws Exception {
        when(userService.partiallyUpdateUser(anyString(), any(Map.class))).thenReturn(user);

        mockMvc.perform(patch(BASE_URL + "/" + TEST_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)));
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(anyString(), any(User.class))).thenReturn(user);

        mockMvc.perform(put(BASE_URL + "/" + TEST_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userToJson(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(TEST_EMAIL)));
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(anyString());

        mockMvc.perform(delete(BASE_URL + "/" + TEST_EMAIL))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchUsersByBirthDateRange() throws Exception {
        when(userService.searchUsersByBirthDateRange(any(LocalDate.class), any(LocalDate.class))).thenReturn(List.of(user));

        mockMvc.perform(get(BASE_URL + "/search")
                        .param("from", "1980-01-01")
                        .param("to", "2000-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is(TEST_EMAIL)));
    }

    private String userToJson(User user) {
        return String.format(
                "{\"email\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\",\"birthDate\":\"%s\",\"address\":\"%s\",\"phoneNumber\":\"%s\"}",
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate().toString(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }
}
