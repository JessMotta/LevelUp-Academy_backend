package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.user.UserLogin;
import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.dto.user.UserResponse;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.Roles;
import com.fiap.hackaton.domain.exceptions.CredentialsException;
import com.fiap.hackaton.domain.exceptions.EmailDuplicate;
import com.fiap.hackaton.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequest userRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest("Test User", "test@example.com", "password");
        user = new User(userRequest);
    }

    @Test
    @DisplayName("Should save user when email does not exist")
    void save_ShouldSaveUser_WhenEmailNotExists() {
        when(repository.findByEmail(userRequest.email())).thenReturn(Optional.empty());

        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        }).when(repository).save(any(User.class));

        Long userId = userService.save(userRequest);

        assertNotNull(userId);
        verify(repository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw EmailDuplicate when email exists")
    void save_ShouldThrowEmailDuplicate_WhenEmailExists() {
        when(repository.findByEmail(userRequest.email())).thenReturn(Optional.of(user));

        assertThrows(EmailDuplicate.class, () -> userService.save(userRequest));
        verify(repository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should return user when user exists")
    void findUserEntityById_ShouldReturnUser_WhenUserExists() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        User foundUser = userService.findUserEntityById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when user does not exist")
    void findUserEntityById_ShouldThrowEntityNotFoundException_WhenUserNotExists() {
        when(repository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findUserEntityById(user.getId()));
    }

    @Test
    @DisplayName("Should return UserResponse when user exists")
    void findById_ShouldReturnUserResponse_WhenUserExists() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        UserResponse userResponse = userService.findById(user.getId());

        assertNotNull(userResponse);
        assertEquals(user.getEmail(), userResponse.email());
    }

    @Test
    @DisplayName("Should update user when email does not exist")
    void update_ShouldUpdateUser_WhenEmailNotExists() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        when(repository.findByEmail(userRequest.email())).thenReturn(Optional.empty());

        doAnswer(invocation -> {
            User updatedUser = invocation.getArgument(0);
            updatedUser.setId(1L);
            return updatedUser;
        }).when(repository).save(any(User.class));

        Long userId = userService.update(user.getId(), userRequest);

        assertNotNull(userId);

        verify(repository).save(any(User.class));
    }


    @Test
    @DisplayName("Should throw EmailDuplicate when email exists")
    void update_ShouldThrowEmailDuplicate_WhenEmailExists() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        when(repository.findByEmail(userRequest.email())).thenReturn(Optional.of(new User(userRequest)));

        assertThrows(EmailDuplicate.class, () -> userService.update(user.getId(), userRequest));
        verify(repository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should return token when credentials are correct")
    void login_ShouldReturnToken_WhenCredentialsAreCorrect() {
        UserLogin userLogin = new UserLogin("test@example.com", "password");
        when(repository.findByEmail(userLogin.email())).thenReturn(Optional.of(user));

        String token = userService.login(userLogin);

        assertNotNull(token);
    }

    @Test
    @DisplayName("Should throw CredentialsException when password is incorrect")
    void login_ShouldThrowCredentialsException_WhenPasswordIsIncorrect() {
        UserLogin userLogin = new UserLogin("test@example.com", "wrongpassword");
        when(repository.findByEmail(userLogin.email())).thenReturn(Optional.of(user));

        assertThrows(CredentialsException.class, () -> userService.login(userLogin));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when email does not exist")
    void login_ShouldThrowEntityNotFoundException_WhenEmailNotExists() {
        UserLogin userLogin = new UserLogin("test@example.com", "password");
        when(repository.findByEmail(userLogin.email())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.login(userLogin));
    }

    @Test
    @DisplayName("Should update user role when user exists")
    void updateRole_ShouldUpdateUserRole_WhenUserExists() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.updateRole(user.getId(), Roles.TEACHER);

        assertEquals(Roles.TEACHER, user.getRole());
        verify(repository).save(user);
    }

}