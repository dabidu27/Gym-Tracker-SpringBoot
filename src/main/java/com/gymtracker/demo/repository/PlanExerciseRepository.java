package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.PlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanExerciseRepository extends JpaRepository<PlanExercise, Long> {

    Optional<PlanExercise> findByWorkoutPlanIdAndExerciseId(Long workoutPlanId, Long exerciseId);
}
