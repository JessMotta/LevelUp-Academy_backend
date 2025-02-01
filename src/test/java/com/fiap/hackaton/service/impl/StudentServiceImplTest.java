package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.student.ListStudentResponse;
import com.fiap.hackaton.domain.dto.student.StudentResponse;
import com.fiap.hackaton.domain.entity.Classroom;
import com.fiap.hackaton.domain.entity.Student;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.Patents;
import com.fiap.hackaton.domain.enums.Roles;
import com.fiap.hackaton.repository.StudentRepository;
import com.fiap.hackaton.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private UserService userService;

    @InjectMocks
    private StudentServiceImpl studentService;

    private User user;
    private Student student;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        student = Student.builder()
                .user(user)
                .experiencePoints(0)
                .currentPatent(Patents.APRENDIZ)
                .build();
        student.setId(1L);
    }

    @Test
    @DisplayName("Should create student for user")
    void create_ShouldCreateStudentForUser() {
        when(userService.findUserEntityById(user.getId())).thenReturn(user);

        doAnswer(invocation -> {
            Student student = invocation.getArgument(0);
            student.setId(1L);
            return student;
        }).when(repository).save(any(Student.class));

        StudentResponse response = studentService.create(user.getId(), "1º Ano A");

        assertNotNull(response);
        assertEquals(student.getId(), response.id());
        verify(userService).updateRole(user.getId(), Roles.STUDENT);
        verify(repository).save(any(Student.class));
    }

    @Test
    @DisplayName("Should return student response when student exists")
    void findById_ShouldReturnStudentResponse_WhenStudentExists() {
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));

        StudentResponse response = studentService.findById(student.getId());

        assertNotNull(response);
        assertEquals(student.getId(), response.id());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when student does not exist")
    void findById_ShouldThrowEntityNotFoundException_WhenStudentNotExists() {
        when(repository.findById(student.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.findById(student.getId()));
    }

    @Test
    @DisplayName("Should find all students")
    void findAll_ShouldReturnAllStudents() {
        List<Student> students = List.of(student);
        when(repository.findAll()).thenReturn(students);

        List<ListStudentResponse> responses = studentService.findAll();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    @DisplayName("Should delete student when student exists")
    void delete_ShouldDeleteStudent_WhenStudentExists() {
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.delete(student.getId());

        verify(repository).delete(student);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existing student")
    void delete_ShouldThrowEntityNotFoundException_WhenStudentNotExists() {
        when(repository.findById(student.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.delete(student.getId()));
    }

    @Test
    @DisplayName("Should add experience points to student")
    void addExperiencePoints_ShouldAddExperiencePointsToStudent() {
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.addExperiencePoints(student.getId(), 10);

        assertEquals(10, student.getExperiencePoints());
        verify(repository).save(student);
    }

    @Test
    @DisplayName("Should add student to classroom")
    void addStudentToClassroom_ShouldAddStudentToClassroom() {
        Classroom classroom = new Classroom();
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.addStudentToClassroom(student.getId(), classroom);

        assertEquals(classroom, student.getClassrooms().get(0));
    }

    @Test
    @DisplayName("Should remove student from classroom")
    void removeStudentFromClassroom_ShouldRemoveStudentFromClassroom() {
        Classroom classroom = new Classroom();
        student.setClassrooms(List.of(classroom));
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.removeStudentFromClassroom(student.getId(), classroom);

        assertEquals(0, student.getClassrooms().size());
    }

}