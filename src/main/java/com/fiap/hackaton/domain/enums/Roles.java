package com.fiap.hackaton.domain.enums;

public enum Roles {
    TEACHER("TEACHER"),
    STUDENT("STUDENT");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
