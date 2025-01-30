package com.fiap.hackaton.domain.enums;

public enum DayOfWeek {
    SEGUNDA_FEIRA("Segunda-feira"),
    TERCA_FEIRA("Terça-feira"),
    QUARTA_FEIRA("Quarta-feira"),
    QUINTA_FEIRA("Quinta-feira"),
    SEXTA_FEIRA("Sexta-feira");

    private final String description;

    DayOfWeek(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
