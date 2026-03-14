package com.gymtracker.demo.controller;

import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.dtos.WorkoutPlanDTOs.CreatePlanRequest;
import com.gymtracker.demo.dtos.WorkoutPlanDTOs.WorkoutPlanResponse;
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

//        Map<String, Object> response = new HashMap<>();
//        response.put("id", workoutPlan.getId());
//        response.put("name", workoutPlan.getName());
//        response.put("user_id", workoutPlan.getUser().getId());
//        response.put("created_at", workoutPlan.getCreatedAt());
//        //we can return directly an object of the DTO class we wrote for WorkoutPlanResponse
//        WorkoutPlanResponse response = new WorkoutPlanResponse(workoutPlan.getName(), workoutPlan.getId(), workoutPlan.getCreatedAt(), workoutPlan.getUpdatedAt());
//
//        return ResponseEntity.status(201).body(response);

        //but we also want to add the user_id to the response
        WorkoutPlanResponse workoutPlanResponse = new WorkoutPlanResponse(workoutPlan.getName(), workoutPlan.getId(), workoutPlan.getCreatedAt(), workoutPlan.getUpdatedAt());
        Map<String, Object> response = new HashMap<>();
        response.put("workout_plan", workoutPlanResponse);
        response.put("user_id", user.getId());
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
        List <WorkoutPlanResponse> workoutPlanResponse = workoutPlans.stream().map(wp -> new WorkoutPlanResponse(wp.getName(), wp.getId(), wp.getCreatedAt(), wp.getUpdatedAt())).collect(Collectors.toList());
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

        WorkoutPlanResponse workoutPlanResponse = new WorkoutPlanResponse(workoutPlan.getName(), workoutPlan.getId(), workoutPlan.getCreatedAt(), workoutPlan.getUpdatedAt());
        Map<String, Object> response = new HashMap<>();
        response.put("workout_plan", workoutPlanResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWorkoutPlan(@PathVariable Long id){

        User user = this.middleware.getCurrentUser();
        WorkoutPlan workoutPlanDeleted = new WorkoutPlan();
        try{
            workoutPlanDeleted = this.workoutPlanService.deleteWorkoutById(id, user);
        }catch (RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        WorkoutPlanResponse workoutPlanResponse = new WorkoutPlanResponse(workoutPlanDeleted.getName(), workoutPlanDeleted.getId(), workoutPlanDeleted.getCreatedAt(), workoutPlanDeleted.getUpdatedAt());
        Map<String, Object> response = new HashMap<>();
        response.put("workout_plan", workoutPlanResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editWorkoutPlan(@Valid @RequestBody CreatePlanRequest req, @PathVariable Long id){

        String newName = req.getName();
        User user = this.middleware.getCurrentUser();
        WorkoutPlan workoutPlanEdited = new WorkoutPlan();
        try{
            workoutPlanEdited = this.workoutPlanService.editWorkoutById(id, user, newName);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        //we can return directly an object of the DTO class we wrote for WorkoutPlanResponse
//        WorkoutPlanResponse response = new WorkoutPlanResponse(workoutPlanEdited.getName(), workoutPlanEdited.getId(), workoutPlanEdited.getCreatedAt(), workoutPlanEdited.getUpdatedAt());
//        return ResponseEntity.status(200).body(response);

        //but we also want to add the userId to the response
        WorkoutPlanResponse workoutPlanResponse = new WorkoutPlanResponse(workoutPlanEdited.getName(), workoutPlanEdited.getId(), workoutPlanEdited.getCreatedAt(), workoutPlanEdited.getUpdatedAt());
        Map<String, Object> response = new HashMap<>();
        response.put("workout_plan", workoutPlanResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);
    }
}
