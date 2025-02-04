package com.fiap.hackaton.controller;

import com.fiap.hackaton.controller.user.CreateUserController;
import com.fiap.hackaton.controller.user.LoginController;
import com.fiap.hackaton.controller.user.UpdateUserController;
import com.fiap.hackaton.domain.dto.user.UserLogin;
import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CreateUserController createUserController;
    @InjectMocks
    private UpdateUserController updateUserController;
    @InjectMocks
    private LoginController loginController;

    @Test
    @DisplayName("Test user creation")
    public void testSave() {
        UserRequest userRequest = new UserRequest(
                "John Doe",
                "johndoe@example.com",
                "password"
        );
        Long userId = 1L;
        when(userService.save(any(UserRequest.class))).thenReturn(userId);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<Long> response = createUserController.execute(userRequest, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userId, response.getBody());
        assertEquals(URI.create("/user/1"), response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Test user login")
    public void testLogin() {
        UserLogin userLogin = new UserLogin("test@example.com", "password");
        String token = "token";
        when(userService.login(any(UserLogin.class))).thenReturn(token);

        ResponseEntity<String> response = loginController.execute(userLogin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody());
    }

    @Test
    @DisplayName("Test update user")
    public void testUpdate() {
        Long userId = 1L;
        UserRequest userRequest = new UserRequest(
                "John Doe",
                "johndoe@example.com",
                "password"
        );
        when(userService.update(any(Long.class), any(UserRequest.class))).thenReturn(userId);

        ResponseEntity<Long> response = updateUserController.execute(userId, userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, response.getBody());
    }

}