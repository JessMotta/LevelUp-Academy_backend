package com.fiap.hackaton.service;

import com.fiap.hackaton.domain.dto.user.UserLogin;
import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.dto.user.UserResponse;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.Roles;

public interface UserService {
    Long save(UserRequest request);
    User findUserEntityById(Long id);
    UserResponse findById(Long id);
    Long update(Long id, UserRequest request);
    String login(UserLogin login);
    void updateRole(Long id, Roles role);
}
