package com.fiap.hackaton.domain.entity;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.domain.enums.Execution;
import com.fiap.hackaton.domain.enums.TypeActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private Activity activity;

    @BeforeEach
    void setUp() {
        ActivityRequest request = new ActivityRequest(
                "Atividade 1",
                "Descrição da atividade 1",
                TypeActivity.MULTIPLA_ESCOLHA,
                false,
                1,
                Execution.EM_CASA,
                10
        );
        activity = new Activity(request);
    }

    @Test
    @DisplayName("Test activity creation")
    void testActivityCreation() {
        assertEquals("Atividade 1", activity.getName());
        assertEquals("Descrição da atividade 1", activity.getDescription());
        assertEquals(TypeActivity.MULTIPLA_ESCOLHA, activity.getType());
        assertFalse(activity.isInGroup());
        assertEquals(1, activity.getStudentsPerGroup());
        assertEquals(Execution.EM_CASA, activity.getExecution());
        assertEquals(10, activity.getPrestigeValue());
    }

    @Test
    @DisplayName("Test update activity")
    void testUpdateActivity() {
        ActivityRequest updateRequest = new ActivityRequest(
                "Atividade 2",
                "Descrição da atividade 2",
                TypeActivity.MULTIPLA_ESCOLHA,
                true,
                2,
                Execution.EM_SALA,
                20
        );
        activity.update(updateRequest);

        assertEquals("Atividade 2", activity.getName());
        assertEquals("Descrição da atividade 2", activity.getDescription());
        assertEquals(TypeActivity.MULTIPLA_ESCOLHA, activity.getType());
        assertTrue(activity.isInGroup());
        assertEquals(2, activity.getStudentsPerGroup());
        assertEquals(Execution.EM_SALA, activity.getExecution());
        assertEquals(20, activity.getPrestigeValue());
    }

}