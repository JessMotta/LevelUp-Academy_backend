package com.fiap.hackaton.domain.enums;

public enum TypeActivity {
    MULTIPLA_ESCOLHA("Múltipla escolha"),
    QUESTIONARIO("Questionário"),
    QUIZ("Quiz");

    private String description;

    TypeActivity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}