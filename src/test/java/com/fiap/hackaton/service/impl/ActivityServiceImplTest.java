package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.domain.dto.activity.ActivityResponse;
import com.fiap.hackaton.domain.entity.Activity;
import com.fiap.hackaton.domain.entity.Classroom;
import com.fiap.hackaton.domain.entity.Student;
import com.fiap.hackaton.domain.enums.Execution;
import com.fiap.hackaton.domain.enums.Patents;
import com.fiap.hackaton.domain.enums.TypeActivity;
import com.fiap.hackaton.domain.exceptions.ImageException;
import com.fiap.hackaton.repository.ActivityRepository;
import com.fiap.hackaton.service.ClassroomService;
import com.fiap.hackaton.service.ImageService;
import com.fiap.hackaton.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

    @Mock
    private ActivityRepository repository;

    @Mock
    private ClassroomService classroomService;

    @Mock
    private StudentService studentService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ActivityServiceImpl activityService;

    private Classroom classroom;
    private Activity activity;
    private ActivityRequest activityRequest;

    @BeforeEach
    void setUp() {
        classroom = new Classroom();
        classroom.setId(1L);

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
        activity.setId(1L);
        activity.setClassroom(classroom);
    }

    @Test
    @DisplayName("Should create activity for classroom")
    void createActivity_ShouldCreateActivityForClassroom() {
        when(classroomService.findClassroomEntityById(classroom.getId())).thenReturn(classroom);

        doAnswer(invocation -> {
            Activity activity = invocation.getArgument(0);
            activity.setId(1L);
            return activity;
        }).when(repository).save(any(Activity.class));

        Long activityId = activityService.createActivity(classroom.getId(), activityRequest);

        assertNotNull(activityId);
        assertEquals(activity.getId(), activityId);
        verify(repository).save(any(Activity.class));
    }

    @Test
    @DisplayName("Should return activity response when activity exists")
    void findActivityById_ShouldReturnActivityResponse_WhenActivityExists() {
        when(repository.findById(activity.getId())).thenReturn(Optional.of(activity));

        ActivityResponse response = activityService.findActivityById(activity.getId());

        assertNotNull(response);
        assertEquals(activity.getId(), response.id());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when activity does not exist")
    void findActivityById_ShouldThrowEntityNotFoundException_WhenActivityNotExists() {
        when(repository.findById(activity.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> activityService.findActivityById(activity.getId()));
    }

    @Test
    @DisplayName("Should update activity")
    void updateActivity_ShouldUpdateActivity() {
        when(repository.findById(activity.getId())).thenReturn(Optional.of(activity));

        activityService.updateActivity(activity.getId(), activityRequest);

        verify(repository).save(activity);
    }

    @Test
    @DisplayName("Should delete activity")
    void deleteActivity_ShouldDeleteActivity() {
        when(repository.findById(activity.getId())).thenReturn(Optional.of(activity));

        activityService.deleteActivity(activity.getId());

        verify(repository).delete(activity);
    }

    @Test
    @DisplayName("Should deliver answers for activity")
    void deliverAnswers_ShouldDeliverAnswersForActivity() {
        MultipartFile file = mock(MultipartFile.class);
        when(repository.findById(activity.getId())).thenReturn(Optional.of(activity));
        when(imageService.uploadImage(file)).thenReturn("test.jpg");

        activityService.deliverAnswers(activity.getId(), file);

        assertEquals("test.jpg", activity.getAnswer());
        verify(repository).save(activity);
    }

    @Test
    @DisplayName("Should throw ImageException when upload fails")
    void deliverAnswers_ShouldThrowImageException_WhenUploadFails() {
        MultipartFile file = mock(MultipartFile.class);
        when(repository.findById(activity.getId())).thenReturn(Optional.of(activity));
        when(imageService.uploadImage(file)).thenThrow(new ImageException("Failed to upload image"));

        assertThrows(ImageException.class, () -> activityService.deliverAnswers(activity.getId(), file));
    }

    @Test
    @DisplayName("Should evaluate activity and add experience points to student")
    void evaluateActivity_ShouldEvaluateActivityAndAddExperiencePointsToStudent() {
        Student student = Student.builder()
                .id(1L)
                .experiencePoints(0)
                .currentPatent(Patents.APRENDIZ)
                .build();
        when(repository.findById(activity.getId())).thenReturn(Optional.of(activity));
        when(studentService.findStudentEntityById(student.getId())).thenReturn(student);

        activityService.evaluateActivity(activity.getId(), student.getId(), 10);

        assertEquals(10, student.getExperiencePoints());
        assertEquals(10, activity.getValueReceived());
        verify(studentService).findStudentEntityById(student.getId());
    }

}