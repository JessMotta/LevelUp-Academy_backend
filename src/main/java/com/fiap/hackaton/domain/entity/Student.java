package com.fiap.hackaton.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fiap.hackaton.domain.enums.Patents;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_lvup_student")
@EqualsAndHashCode(of = "id")
@Builder
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer experiencePoints;
    @Enumerated(EnumType.STRING)
    private Patents currentPatent;
    private String grade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "t_lvup_student_classroom",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id")
    )
    private List<Classroom> classrooms;

    public void addExperiencePoints(Integer points) {
        this.experiencePoints += points;
    }

    public int verifyPatent() {
        if (this.experiencePoints >= 17280) {
            this.currentPatent = Patents.ESPECIALISTA;
            return 0;
        } else if (this.experiencePoints >= 13440) {
            this.currentPatent = Patents.CIENTISTA;
            return 17280;
        } else if (this.experiencePoints >= 10080) {
            this.currentPatent = Patents.EXPLORADOR;
            return 13440;
        } else if (this.experiencePoints >= 6720) {
            this.currentPatent = Patents.ASSISTENTE;
            return 10080;
        } else if (this.experiencePoints >= 3840) {
            this.currentPatent = Patents.MONITOR;
            return 6720;
        } else if (this.experiencePoints >= 1920) {
            this.currentPatent = Patents.APRENDIZ;
            return 3840;
        } else {
            this.currentPatent = Patents.INICIANTE;
            return 1920;
        }
    }

}
