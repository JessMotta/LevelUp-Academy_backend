package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.classroom.ClassroomDetails;
import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import com.fiap.hackaton.domain.dto.classroom.ListClassroom;
import com.fiap.hackaton.domain.entity.Classroom;
import com.fiap.hackaton.domain.entity.Student;
import com.fiap.hackaton.domain.entity.Teacher;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.DayOfWeek;
import com.fiap.hackaton.domain.enums.Roles;
import com.fiap.hackaton.repository.ClassroomRepository;
import com.fiap.hackaton.service.StudentService;
import com.fiap.hackaton.service.TeacherService;
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
class ClassroomServiceImplTest {

    @Mock
    private ClassroomRepository repository;

    @Mock
    private TeacherService teacherService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    private Teacher teacher;
    private Classroom classroom;
    private ClassroomRequest classroomRequest;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "John Doe", "johndoe@example.com", "123456789", Roles.TEACHER);
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setUser(user);

        classroomRequest = new ClassroomRequest(
                "Matemática",
                DayOfWeek.SEGUNDA_FEIRA,
                1
        );
        classroom = new Classroom(classroomRequest);
        classroom.setId(1L);
        classroom.setTeacher(teacher);
    }

    @Test
    @DisplayName("Should create classroom for teacher")
    void createClassroom_ShouldCreateClassroomForTeacher() {
        when(teacherService.findTeacherEntityById(teacher.getId())).thenReturn(teacher);

        doAnswer(invocation -> {
            Classroom classroom = invocation.getArgument(0);
            classroom.setId(1L);
            return classroom;
        }).when(repository).save(any(Classroom.class));

        Long classroomId = classroomService.createClassroom(teacher.getId(), classroomRequest);

        assertNotNull(classroomId);
        assertEquals(classroom.getId(), classroomId);
        verify(repository).save(any(Classroom.class));
    }

    @Test
    @DisplayName("Should list all classrooms")
    void listClassrooms_ShouldReturnAllClassrooms() {
        List<Classroom> classrooms = List.of(classroom);
        when(repository.findAll()).thenReturn(classrooms);

        List<ListClassroom> responses = classroomService.listClassrooms();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    @DisplayName("Should return classroom details when classroom exists")
    void findClassroomById_ShouldReturnClassroomDetails_WhenClassroomExists() {
        when(repository.findById(classroom.getId())).thenReturn(Optional.of(classroom));

        ClassroomDetails response = classroomService.findClassroomById(classroom.getId());

        assertNotNull(response);
        assertEquals(classroom.getId(), response.id());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when classroom does not exist")
    void findClassroomById_ShouldThrowEntityNotFoundException_WhenClassroomNotExists() {
        when(repository.findById(classroom.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> classroomService.findClassroomById(classroom.getId()));
    }

    @Test
    @DisplayName("Should update classroom")
    void updateClassroom_ShouldUpdateClassroom() {
        when(repository.findById(classroom.getId())).thenReturn(Optional.of(classroom));

        classroomService.updateClassroom(classroom.getId(), classroomRequest);

        verify(repository).save(classroom);
    }

    @Test
    @DisplayName("Should delete classroom")
    void deleteClassroom_ShouldDeleteClassroom() {
        when(repository.findById(classroom.getId())).thenReturn(Optional.of(classroom));

        classroomService.deleteClassroom(classroom.getId());

        verify(repository).delete(classroom);
    }

    @Test
    @DisplayName("Should add student to classroom")
    void addStudent_ShouldAddStudentToClassroom() {
        Student student = new Student();
        student.setId(1L);
        when(repository.findById(classroom.getId())).thenReturn(Optional.of(classroom));

        classroomService.addStudent(classroom.getId(), student.getId());

        verify(studentService).addStudentToClassroom(student.getId(), classroom);
    }

    @Test
    @DisplayName("Should remove student from classroom")
    void removeStudent_ShouldRemoveStudentFromClassroom() {
        Student student = new Student();
        student.setId(1L);
        when(repository.findById(classroom.getId())).thenReturn(Optional.of(classroom));

        classroomService.removeStudent(classroom.getId(), student.getId());

        verify(studentService).removeStudentFromClassroom(student.getId(), classroom);
    }

}