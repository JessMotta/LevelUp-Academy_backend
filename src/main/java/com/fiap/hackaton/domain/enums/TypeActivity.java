package com.fiap.hackaton.domain.enums;

public enum TypeActivity {
    MULTIPLA_ESCOLHA("Múltipla escolha"),
    QUESTIONARIO("Questionário"),
    QUIZ("Quiz"),
    ESTUDO_DIRIGIDO("Estudo dirigido"),
    APRESENTACAO("Apresentação"),
    CACA_PALAVRAS("Caça palavras"),
    CAMPEONATO("Campeonato"),;

    private String description;

    TypeActivity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
