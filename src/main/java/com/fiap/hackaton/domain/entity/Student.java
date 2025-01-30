package com.fiap.hackaton.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fiap.hackaton.domain.enums.Patents;
import jakarta.persistence.*;
import lombok.*;

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    public void addExperiencePoints(Integer points) {
        this.experiencePoints += points;
    }

    public void verifyPatent() {
        if (this.experiencePoints >= 17280) {
            this.currentPatent = Patents.ESPECIALISTA;
        } else if(this.experiencePoints >= 13440) {
            this.currentPatent = Patents.CIENTISTA;
        } else if(this.experiencePoints >= 10080) {
            this.currentPatent = Patents.EXPLORADOR;
        } else if(this.experiencePoints >= 6720) {
            this.currentPatent = Patents.ASSISTENTE;
        } else if(this.experiencePoints >= 3840) {
            this.currentPatent = Patents.MONITOR;
        } else if(this.experiencePoints >= 1920) {
            this.currentPatent = Patents.APRENDIZ;
        } else {
            this.currentPatent = Patents.INICIANTE;
        }
    }

}
