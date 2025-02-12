package com.fiap.hackaton.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_lvup_teacher")
@EqualsAndHashCode(of = "id")
@Builder
public class Teacher {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "teacher")
    private List<Classroom> classrooms;

    public Teacher(User user) {
        this.user = user;
        this.classrooms = new ArrayList<>();
    }

}
