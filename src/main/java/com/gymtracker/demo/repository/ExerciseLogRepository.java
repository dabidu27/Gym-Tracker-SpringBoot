package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseLogRepository extends JpaRepository <ExerciseLog, Long> {

}
