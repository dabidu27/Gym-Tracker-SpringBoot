package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.ExerciseLog;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseLogRepository extends JpaRepository <ExerciseLog, Long> {

    Optional<ExerciseLog> findByIdAndWorkoutSession(Long id, WorkoutSession ws);
}
