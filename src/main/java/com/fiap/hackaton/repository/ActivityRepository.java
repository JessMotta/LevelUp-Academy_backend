package com.fiap.hackaton.repository;

import com.fiap.hackaton.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
