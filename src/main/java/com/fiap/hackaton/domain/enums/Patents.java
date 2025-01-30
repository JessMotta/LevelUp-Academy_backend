package com.fiap.hackaton.domain.enums;

public enum Patents {
    APRENDIZ("Aprendiz"),
    INICIANTE("Iniciante"),
    MONITOR("Monitor"),
    ASSISTENTE("Assistente"),
    EXPLORADOR("Explorador"),
    CIENTISTA("Cientista"),
    ESPECIALISTA("Especialista");

    private String patent;

    Patents(String patent) {
        this.patent = patent;
    }

    public String getPatent() {
        return patent;
    }
}
