package com.fiap.hackaton.repository;

import com.fiap.hackaton.domain.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}
