package com.fiap.hackaton.domain.entity;

import com.fiap.hackaton.domain.enums.Patents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .id(1L)
                .experiencePoints(0)
                .currentPatent(Patents.APRENDIZ)
                .build();
    }

    @Test
    @DisplayName("Test verify patent - APRENDIZ to INICIANTE")
    void testVerifyPatentToIniciante() {
        student.setExperiencePoints(1000); // Definir diretamente
        student.verifyPatent();
        assertEquals(Patents.INICIANTE, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - INICIANTE to MONITOR")
    void testVerifyPatentToMonitor() {
        student.setExperiencePoints(2500);
        student.verifyPatent();
        assertEquals(Patents.MONITOR, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - MONITOR to ASSISTENTE")
    void testVerifyPatentToAssistente() {
        student.setExperiencePoints(5000);
        student.verifyPatent();
        assertEquals(Patents.ASSISTENTE, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - ASSISTENTE to EXPLORADOR")
    void testVerifyPatentToExplorador() {
        student.setExperiencePoints(8000);
        student.verifyPatent();
        assertEquals(Patents.EXPLORADOR, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - EXPLORADOR to CIENTISTA")
    void testVerifyPatentToLider() {
        student.setExperiencePoints(12000);
        student.verifyPatent();
        assertEquals(Patents.CIENTISTA, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - LIDER to ESPECIALISTA")
    void testVerifyPatentToEspecialista() {
        student.setExperiencePoints(15000);
        student.verifyPatent();
        assertEquals(Patents.ESPECIALISTA, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - default case (APRENDIZ)")
    void testVerifyPatentToAprendiz() {
        student.setExperiencePoints(500); // Valor que não está listado no switch
        student.verifyPatent();
        assertEquals(Patents.APRENDIZ, student.getCurrentPatent());
    }

}