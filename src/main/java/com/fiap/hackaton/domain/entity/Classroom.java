package com.fiap.hackaton.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_classroom")
@EqualsAndHashCode(of = "id")
public class Classroom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private Integer dayOfWeek;
    private Integer timeSpot;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "classroom")
    private List<Student> students;

    @OneToMany(mappedBy = "classroom")
    private List<Activity> activities;

    public Classroom(ClassroomRequest request) {
        this.subject = request.subject();
        this.dayOfWeek = request.dayOfWeek();
        this.timeSpot = request.timeSpot();
        this.students = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    public void update(ClassroomRequest request) {
        if (request.subject() != null) this.subject = request.subject();
        if (request.dayOfWeek() != null) this.dayOfWeek = request.dayOfWeek();
        if (request.timeSpot() != null) this.timeSpot = request.timeSpot();
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }
}
