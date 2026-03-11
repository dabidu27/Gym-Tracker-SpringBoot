package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    public Optional<WorkoutPlan> findByNameAndUser(String name, User user);
    //public Optional<List<WorkoutPlan>> findByUser(User user);
    //the method above should not return Optional, because a List is never null, it is just empty
    public List<WorkoutPlan> findByUser(User user);
    public Optional<WorkoutPlan> findByIdAndUser(Long id, User user);
}
