package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    public Optional<WorkoutPlan> findByName(String name);
    public Optional<List<WorkoutPlan>> findByUser(User user);
}
