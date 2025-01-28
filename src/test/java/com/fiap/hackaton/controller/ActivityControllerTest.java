package com.fiap.hackaton.controller;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.domain.dto.activity.ActivityResponse;
import com.fiap.hackaton.domain.entity.Activity;
import com.fiap.hackaton.domain.enums.Execution;
import com.fiap.hackaton.domain.enums.TypeActivity;
import com.fiap.hackaton.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ActivityControllerTest {

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    private Activity activity;
    private ActivityRequest activityRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        activityRequest = new ActivityRequest(
                "Atividade 1",
                "Descrição da atividade 1",
                TypeActivity.MULTIPLA_ESCOLHA,
                false,
                1,
                Execution.EM_CASA,
                10
        );
        activity = new Activity(activityRequest);
    }

    @Test
    @DisplayName("Test create activity")
    public void testCreate() {
        Long classroomId = 1L;
        Long activityId = 1L;
        when(activityService.createActivity(any(Long.class), any(ActivityRequest.class))).thenReturn(activityId);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<Long> response = activityController.create(classroomId, activityRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activityId, response.getBody());
    }

    @Test
    @DisplayName("Test find activity by ID")
    public void testFindById() {
        Long activityId = 1L;
        ActivityResponse activityResponse = new ActivityResponse(activity);
        when(activityService.findActivityById(activityId)).thenReturn(activityResponse);

        ResponseEntity<ActivityResponse> response = activityController.findById(activityId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activityResponse, response.getBody());
    }

    @Test
    @DisplayName("Test update activity")
    public void testUpdate() {
        Long activityId = 1L;
        when(activityService.updateActivity(any(Long.class), any(ActivityRequest.class))).thenReturn(activityId);

        ResponseEntity<Long> response = activityController.update(activityId, activityRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activityId, response.getBody());
    }

    @Test
    @DisplayName("Test deliver answers")
    public void testDeliverAnswers() {
        Long activityId = 1L;
        MockMultipartFile file = new MockMultipartFile("file", "answers.txt", "text/plain", "answers".getBytes());

        ResponseEntity<Void> response = activityController.deliverAnswers(activityId, file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Test evaluate activity")
    public void testEvaluateActivity() {
        Long activityId = 1L;
        Long studentId = 1L;
        Integer value = 10;

        ResponseEntity<Void> response = activityController.evaluateActivity(activityId, studentId, value);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Test delete activity by ID")
    public void testDelete() {
        Long activityId = 1L;

        ResponseEntity<Void> response = activityController.delete(activityId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}