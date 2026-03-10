package com.gymtracker.demo.service;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutPlan;
import com.gymtracker.demo.repository.WorkoutPlanRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutPlanService {

    @Autowired
    WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlan createWorkoutPlan(String name, User user){

        if(this.workoutPlanRepository.findByNameAndUser(name, user).isPresent()){
            throw new RuntimeException("You already have a workout plan with this name");
        }

        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setUser(user);
        workoutPlan.setName(name);
        return this.workoutPlanRepository.save(workoutPlan);
    }

    public List<WorkoutPlan> getAllWorkouts(User user){
        return this.workoutPlanRepository.findByUser(user);
    }

    public WorkoutPlan getWorkoutById(Long id, User user){

        return this.workoutPlanRepository.findByIdAndUser(id, user).orElseThrow(() -> new RuntimeException("Couldn't find workout plan"));
    }

    //make the delete method @Transactional, as Spring needs an active transaction to make delete operations
    //@Transactional opens a transaction for the duration of the method
    @Transactional
    public WorkoutPlan deleteWorkoutById(Long id, User user){
        return this.workoutPlanRepository.deleteByIdAndUser(id, user).orElseThrow(() -> new RuntimeException("Couldn't delete workout plan"));
    }
}
