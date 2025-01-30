package com.fiap.hackaton.domain.entity;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.domain.enums.Execution;
import com.fiap.hackaton.domain.enums.TypeActivity;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_lvup_activity")
@EqualsAndHashCode(of = "id")
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private TypeActivity type;
    private boolean inGroup;
    private Integer studentsPerGroup;
    private String answer;
    @Enumerated(EnumType.STRING)
    private Execution execution;
    private Integer prestigeValue;
    private Integer experienceReceived;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    public Activity(ActivityRequest request) {
        this.name = request.name();
        this.description = request.description();
        this.type = request.type();
        this.inGroup = request.inGroup();
        this.studentsPerGroup = request.studentsPerGroup();
        this.execution = request.execution();
        this.prestigeValue = request.prestigeValue();
        this.completed = false;
    }

    public void update(ActivityRequest request) {
        if (request.name() != null) this.name = request.name();
        if (request.description() != null) this.description = request.description();
        if (request.type() != null) this.type = request.type();
        this.inGroup = request.inGroup();
        if (request.studentsPerGroup() != null) this.studentsPerGroup = request.studentsPerGroup();
        if (request.execution() != null) this.execution = request.execution();
        if (request.prestigeValue() != null) this.prestigeValue = request.prestigeValue();
    }

}
