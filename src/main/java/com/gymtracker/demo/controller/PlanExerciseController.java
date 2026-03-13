package com.gymtracker.demo.controller;

import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.dtos.AddExerciseToPlanRequest;
import com.gymtracker.demo.dtos.EditPlanExercise;
import com.gymtracker.demo.dtos.PlanExerciseResponse;
import com.gymtracker.demo.entity.PlanExercise;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.service.PlanExerciseService;
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
        planExerciseResponse.setId(planExercise.getId());
        planExerciseResponse.setExerciseId(planExercise.getExercise().getId());
        planExerciseResponse.setWorkoutPlanId(planExercise.getWorkoutPlan().getId());
        planExerciseResponse.setTargetSets(planExercise.getTargetSets());
        planExerciseResponse.setTargetReps(planExercise.getTargetReps());

        Map<String, Object> response = new HashMap<>();
        response.put("plan_exercise", planExerciseResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{workoutPlanId}/exercises")
    public ResponseEntity<Object> getAllExercises(@PathVariable Long workoutPlanId){

        User user = this.middleware.getCurrentUser();
        List<PlanExercise> exercisesOfPlan = new ArrayList<>();
        try{
            exercisesOfPlan = this.planExerciseService.getAllExercises(workoutPlanId, user);
        }catch (RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        List<PlanExerciseResponse> responseList = exercisesOfPlan.stream().map(ep -> new PlanExerciseResponse(ep.getId(), ep.getExercise().getId(), ep.getWorkoutPlan().getId(), ep.getTargetSets(), ep.getTargetReps())).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("plan_exercises", responseList);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{workoutPlanId}/exercises/{planExerciseId}")
    //we modify a PlanExercise object so the user can change number of sets or reps
    public ResponseEntity<Object> editSetsAndReps(@PathVariable Long workoutPlanId, @PathVariable Long planExerciseId, @Valid @RequestBody EditPlanExercise req){

        Integer newSets = req.getSets();
        Integer newReps = req.getReps();
        User user = this.middleware.getCurrentUser();
        PlanExercise planExercise = new PlanExercise();
        try{
            planExercise = this.planExerciseService.editSetsAndReps(workoutPlanId, planExerciseId, user, newSets, newReps);
        }catch (RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }


        PlanExerciseResponse planExerciseResponse = new PlanExerciseResponse();
        planExerciseResponse.setId(planExercise.getId());
        planExerciseResponse.setExerciseId(planExercise.getExercise().getId());
        planExerciseResponse.setWorkoutPlanId(planExercise.getWorkoutPlan().getId());
        planExerciseResponse.setTargetSets(planExercise.getTargetSets());
        planExerciseResponse.setTargetReps(planExercise.getTargetReps());

        Map<String, Object> response = new HashMap<>();
        response.put("plan_exercise", planExerciseResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);
    }
}
