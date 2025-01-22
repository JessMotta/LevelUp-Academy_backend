package com.fiap.hackaton.domain.enums;

public enum Execution {
    EM_CASA("Em casa"),
    EM_SALA("Em sala");

    private String description;

    Execution(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
