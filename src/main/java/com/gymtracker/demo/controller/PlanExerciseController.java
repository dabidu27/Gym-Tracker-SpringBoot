package com.gymtracker.demo.controller;

import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.dtos.AddExerciseToPlanRequest;
import com.gymtracker.demo.dtos.PlanExerciseResponse;
import com.gymtracker.demo.entity.PlanExercise;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.service.PlanExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
//2 rest controllers can have the same request mapping as long as the full paths don't clash
@RequestMapping("api/workout_plan")
public class PlanExerciseController {

    @Autowired
    PlanExerciseService planExerciseService;

    @Autowired
    Middleware middleware;

    @PostMapping("/{workoutPlanId}/exercises")
    public ResponseEntity<Object> addExerciseToPlan(@PathVariable Long workoutPlanId, @Valid @RequestBody AddExerciseToPlanRequest req){

        Long exerciseId = req.getExerciseId();
        Integer targetSets = req.getTargetSets();
        Integer targetReps = req.getTargetReps();
        User user = this.middleware.getCurrentUser();

        PlanExercise planExercise = new PlanExercise();
        try{
            planExercise = this.planExerciseService.addExerciseToPlan(user, workoutPlanId, exerciseId, targetSets, targetReps);
        }catch (RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        PlanExerciseResponse planExerciseResponse = new PlanExerciseResponse();
        planExerciseResponse.setExerciseId(planExercise.getExercise().getId());
        planExerciseResponse.setWorkoutPlanId(planExercise.getWorkoutPlan().getId());
        planExerciseResponse.setTargetSets(planExercise.getTargetSets());
        planExerciseResponse.setTargetReps(planExercise.getTargetReps());

        Map<String, Object> response = new HashMap<>();
        response.put("plan_exercise", planExerciseResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(201).body(response);
    }
}
