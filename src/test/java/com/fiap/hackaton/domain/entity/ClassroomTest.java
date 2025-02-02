package com.fiap.hackaton.domain.entity;

import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import com.fiap.hackaton.domain.enums.DayOfWeek;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassroomTest {

    private Classroom classroom;

    @BeforeEach
    void setUp() {
        ClassroomRequest request = new ClassroomRequest("Math", DayOfWeek.SEGUNDA_FEIRA, 10);
        classroom = new Classroom(request);
    }

    @Test
    @DisplayName("Test classroom creation")
    void testClassroomCreation() {
        assertEquals("Math", classroom.getSubject());
        assertEquals(1, classroom.getDayOfWeek());
        assertEquals(10, classroom.getTimeSpot());
        assertTrue(classroom.getStudents().isEmpty());
        assertTrue(classroom.getActivities().isEmpty());
    }

    @Test
    @DisplayName("Test update classroom")
    void testUpdateClassroom() {
        ClassroomRequest updateRequest = new ClassroomRequest("Science", DayOfWeek.TERCA_FEIRA, 11);
        classroom.update(updateRequest);

        assertEquals("Science", classroom.getSubject());
        assertEquals(2, classroom.getDayOfWeek());
        assertEquals(11, classroom.getTimeSpot());
    }

    @Test
    @DisplayName("Test add student")
    void testAddStudent() {
        Student student = new Student();
        classroom.addStudent(student);

        assertEquals(1, classroom.getStudents().size());
        assertTrue(classroom.getStudents().contains(student));
    }

    @Test
    @DisplayName("Test remove student")
    void testRemoveStudent() {
        Student student = new Student();
        classroom.addStudent(student);
        classroom.removeStudent(student);

        assertTrue(classroom.getStudents().isEmpty());
    }

}