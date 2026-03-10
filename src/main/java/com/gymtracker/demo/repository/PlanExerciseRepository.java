package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.PlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanExerciseRepository extends JpaRepository<PlanExercise, Long> {
}
