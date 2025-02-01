package com.fiap.hackaton.domain.entity;

import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.enums.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        UserRequest request = new UserRequest("John Doe", "johndoe@example.com", "password");
        user = new User(request);
    }

    @Test
    @DisplayName("Test user creation")
    void testUserCreation() {
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    @DisplayName("Test update user")
    void testUpdateUser() {
        UserRequest updateRequest = new UserRequest("Jane Doe", "janedoe@example.com", "newpassword");
        user.update(updateRequest);

        assertEquals("Jane Doe", user.getName());
        assertEquals("janedoe@example.com", user.getEmail());
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    @DisplayName("Test set role")
    void testSetRole() {
        user.setRole(Roles.TEACHER);

        assertEquals(Roles.TEACHER, user.getRole());
    }

}