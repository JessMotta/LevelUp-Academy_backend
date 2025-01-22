package com.fiap.hackaton.domain.entity;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.domain.enums.Execution;
import com.fiap.hackaton.domain.enums.TypeActivity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_activity")
@EqualsAndHashCode(of = "id")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeActivity type;
    @Column(nullable = false)
    private boolean inGroup;
    private Integer studentsPerGroup;
    private String answer;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Execution execution;
    @Column(nullable = false)
    private Integer prestigeValue;
    private Integer valueReceived;

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
