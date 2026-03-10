package com.gymtracker.demo.service;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutPlan;
import com.gymtracker.demo.repository.WorkoutPlanRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WorkoutPlanService {

    @Autowired
    WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlan createWorkoutPlan(String name, User user){

        if(this.workoutPlanRepository.findByName(name).isPresent()){
            throw new RuntimeException("You already have a workout plan with this name");
        }

        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setUser(user);
        workoutPlan.setName(name);
        return this.workoutPlanRepository.save(workoutPlan);
    }

}
