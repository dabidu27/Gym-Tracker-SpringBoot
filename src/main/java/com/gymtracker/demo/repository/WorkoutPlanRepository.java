package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    public Optional<WorkoutPlan> findByName(String name);
}
