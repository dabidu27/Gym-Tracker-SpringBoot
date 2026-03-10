package com.gymtracker.demo.controller;

import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.dtos.CreatePlanRequest;
import com.gymtracker.demo.dtos.WorkoutPlanResponse;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutPlan;
import com.gymtracker.demo.service.WorkoutPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workout_plan")
public class WorkoutPlanController {

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

    @GetMapping
    public ResponseEntity<Object> getWorkoutPlans(){

        User user = this.middleware.getCurrentUser();
        List<WorkoutPlan> workoutPlans = new ArrayList<>();
        try{
            workoutPlans = this.workoutPlanService.getAllWorkouts(user);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

//        List<WorkoutPlanResponse> workoutPlanResponse = new ArrayList<>();
//        for(WorkoutPlan wp: workoutPlans){
//            WorkoutPlanResponse wpResponse = new WorkoutPlanResponse(wp.getName(), wp.getId(), wp.getCreatedAt());
//            workoutPlanResponse.add(wpResponse);
//        }

        //we can use a stream + mapping to replace the for above
        //with .stream() we basically "unpack" the list into individual elements (like itterating through them)
        //with .map() we use each WorkoutPlan object to create a new WorkoutPlanResponse object
        //then, because we use .stream() and unpacked the list, we need to use .collect() to transform the data back into a list
        List <WorkoutPlanResponse> workoutPlanResponse = workoutPlans.stream().map(wp -> new WorkoutPlanResponse(wp.getName(), wp.getId(), wp.getCreatedAt())).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("workout_plans", workoutPlanResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getWorkoutPlanById(@PathVariable Long id){

        User user = this.middleware.getCurrentUser();
        WorkoutPlan workoutPlan = new WorkoutPlan();
        try{
            workoutPlan = this.workoutPlanService.getWorkoutById(id, user);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(404).body(response);
        }

        WorkoutPlanResponse workoutPlanResponse = new WorkoutPlanResponse(workoutPlan.getName(), workoutPlan.getId(), workoutPlan.getCreatedAt());
        Map<String, Object> response = new HashMap<>();
        response.put("workout_plan", workoutPlanResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);
    }
}
