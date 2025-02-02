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
                .currentPatent(Patents.INICIANTE)
                .build();
    }

    @Test
    @DisplayName("Test verify patent - INICIANTE to APRENDIZ")
    void testVerifyPatentToIniciante() {
        student.setExperiencePoints(1920);
        int result = student.verifyPatent();
        assertEquals(3840, result);
        assertEquals(Patents.APRENDIZ, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - APRENDIZ to MONITOR")
    void testVerifyPatentToMonitor() {
        student.setExperiencePoints(3840);
        int result = student.verifyPatent();
        assertEquals(6720, result);
        assertEquals(Patents.MONITOR, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - MONITOR to ASSISTENTE")
    void testVerifyPatentToAssistente() {
        student.setExperiencePoints(6720);
        int result = student.verifyPatent();
        assertEquals(10080, result);
        assertEquals(Patents.ASSISTENTE, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - ASSISTENTE to EXPLORADOR")
    void testVerifyPatentToExplorador() {
        student.setExperiencePoints(10080);
        int result = student.verifyPatent();
        assertEquals(13440, result);
        assertEquals(Patents.EXPLORADOR, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - EXPLORADOR to CIENTISTA")
    void testVerifyPatentToLider() {
        student.setExperiencePoints(13440);
        int result = student.verifyPatent();
        assertEquals(17280, result);
        assertEquals(Patents.CIENTISTA, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - LIDER to ESPECIALISTA")
    void testVerifyPatentToEspecialista() {
        student.setExperiencePoints(17280);
        int result = student.verifyPatent();
        assertEquals(0, result);
        assertEquals(Patents.ESPECIALISTA, student.getCurrentPatent());
    }

    @Test
    @DisplayName("Test verify patent - Initial APRENDIZ")
    void testVerifyPatentToAprendiz() {
        student.setExperiencePoints(0);
        int result = student.verifyPatent();
        assertEquals(1920, result);
        assertEquals(Patents.INICIANTE, student.getCurrentPatent());
    }

}