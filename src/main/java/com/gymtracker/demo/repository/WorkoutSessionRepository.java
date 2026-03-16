package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    public Optional<WorkoutSession> findByIdAndUser(Long id, User user);
}
