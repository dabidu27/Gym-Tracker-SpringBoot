package com.gymtracker.demo.controller;

import com.gymtracker.demo.auth.JwtService;
import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.dtos.CreatePlanRequest;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutPlan;
import com.gymtracker.demo.service.WorkoutPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/workout_plan")
public class WorkoutController {

    @Autowired
    WorkoutPlanService workoutPlanService;

    @Autowired
    Middleware middleware;
    @PostMapping
    public ResponseEntity<Object> postWorkoutPlan(@Valid @RequestBody CreatePlanRequest req){

        String name = req.getName();
        User user = middleware.getCurrentUser();

        WorkoutPlan workoutPlan = new WorkoutPlan();
        try{
            workoutPlan = this.workoutPlanService.createWorkoutPlan(name, user);
        }catch(RuntimeException e){

            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", workoutPlan.getId());
        response.put("name", workoutPlan.getName());
        response.put("user_id", workoutPlan.getUser().getId());
        response.put("created_at", workoutPlan.getCreatedAt());

        return ResponseEntity.status(201).body(response);
    }
}
