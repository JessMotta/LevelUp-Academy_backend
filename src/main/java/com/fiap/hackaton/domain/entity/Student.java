package com.fiap.hackaton.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fiap.hackaton.domain.enums.Patents;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_student")
@EqualsAndHashCode(of = "id")
@Builder
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer experiencePoints;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Patents currentPatent;

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
        if (this.experiencePoints >= 15000) {
            this.currentPatent = Patents.ESPECIALISTA;
        } else if(this.experiencePoints >= 12000) {
            this.currentPatent = Patents.LIDER;
        } else if(this.experiencePoints >= 8000) {
            this.currentPatent = Patents.EXPLORADOR;
        } else if(this.experiencePoints >= 5000) {
            this.currentPatent = Patents.ASSISTENTE;
        } else if(this.experiencePoints >= 2500) {
            this.currentPatent = Patents.MONITOR;
        } else if(this.experiencePoints >= 1000) {
            this.currentPatent = Patents.INICIANTE;
        } else {
            this.currentPatent = Patents.APRENDIZ;
        }
    }

}
