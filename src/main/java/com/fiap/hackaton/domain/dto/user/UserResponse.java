package com.fiap.hackaton.domain.dto.user;

import com.fiap.hackaton.domain.entity.User;

public record UserResponse(
        String name,
        String email,
        String password
) {
    public UserResponse(User user) {
        this(
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
