package com.fiap.hackaton.repository;

import com.fiap.hackaton.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
