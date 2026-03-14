package com.gymtracker.demo.service;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutPlan;
import com.gymtracker.demo.entity.WorkoutSession;
import com.gymtracker.demo.repository.WorkoutPlanRepository;
import com.gymtracker.demo.repository.WorkoutSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutSessionService {

    @Autowired
    WorkoutSessionRepository workoutSessionRepository;
    @Autowired
    WorkoutPlanRepository workoutPlanRepository;

    public WorkoutSession startWorkoutSession(Long workoutPlanId, User user){

        WorkoutSession workoutSession = new WorkoutSession();

        WorkoutPlan workoutPlan = this.workoutPlanRepository.findByIdAndUser(workoutPlanId, user).orElseThrow(() -> new RuntimeException("Could not find workout plan"));
        workoutSession.setWorkoutPlan(workoutPlan);
        workoutSession.setUser(user);
        //workoutSession.setEndedAt(null); - endedAt field is nullable and defaults to null automatically

        return this.workoutSessionRepository.save(workoutSession);
    }
}
