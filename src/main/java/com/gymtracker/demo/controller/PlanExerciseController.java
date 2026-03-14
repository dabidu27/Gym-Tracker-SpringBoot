package com.gymtracker.demo.controller;

import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.dtos.ExerciseDTOs.ExerciseResponse;
import com.gymtracker.demo.dtos.PlanExerciseDTOs.AddExerciseToPlanRequest;
import com.gymtracker.demo.dtos.PlanExerciseDTOs.EditPlanExercise;
import com.gymtracker.demo.dtos.PlanExerciseDTOs.PlanExerciseResponse;
import com.gymtracker.demo.dtos.WorkoutPlanDTOs.WorkoutPlanResponse;
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

        ExerciseResponse exerciseResponse = new ExerciseResponse(planExercise.getExercise().getId(), planExercise.getExercise().getName(), planExercise.getExercise().getMuscleGroup());
        WorkoutPlanResponse wpr = new WorkoutPlanResponse(planExercise.getWorkoutPlan().getName(), planExercise.getWorkoutPlan().getId(), planExercise.getWorkoutPlan().getCreatedAt(), planExercise.getWorkoutPlan().getUpdatedAt());
        planExerciseResponse.setExerciseResponse(exerciseResponse);
        planExerciseResponse.setWorkoutPlanResponse(wpr);
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

        List<PlanExerciseResponse> responseList = exercisesOfPlan.stream().map(ep -> {
            PlanExerciseResponse r = new PlanExerciseResponse();
            r.setId(ep.getId());
            r.setExerciseResponse(new ExerciseResponse(ep.getExercise().getId(), ep.getExercise().getName(), ep.getExercise().getMuscleGroup()));
            r.setWorkoutPlanResponse(new WorkoutPlanResponse(ep.getWorkoutPlan().getName(), ep.getWorkoutPlan().getId(), ep.getWorkoutPlan().getCreatedAt(), ep.getWorkoutPlan().getUpdatedAt()));
            r.setTargetSets(ep.getTargetSets());
            r.setTargetReps(ep.getTargetReps());
            return r;
        }).collect(Collectors.toList());
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

        ExerciseResponse exerciseResponse = new ExerciseResponse(planExercise.getExercise().getId(), planExercise.getExercise().getName(), planExercise.getExercise().getMuscleGroup());
        WorkoutPlanResponse wpr = new WorkoutPlanResponse(planExercise.getWorkoutPlan().getName(), planExercise.getWorkoutPlan().getId(), planExercise.getWorkoutPlan().getCreatedAt(), planExercise.getWorkoutPlan().getUpdatedAt());

        planExerciseResponse.setExerciseResponse(exerciseResponse);
        planExerciseResponse.setWorkoutPlanResponse(wpr);
        planExerciseResponse.setTargetSets(planExercise.getTargetSets());
        planExerciseResponse.setTargetReps(planExercise.getTargetReps());

        Map<String, Object> response = new HashMap<>();
        response.put("plan_exercise", planExerciseResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{workoutPlanId}/exercises/{planExerciseId}")
    public ResponseEntity<Object> deletePlanExercise(@PathVariable Long workoutPlanId, @PathVariable Long planExerciseId){

        User user = this.middleware.getCurrentUser();
        PlanExercise deletedPlan = new PlanExercise();
        try{
            deletedPlan = this.planExerciseService.deleteExercise(workoutPlanId, planExerciseId, user);
        }catch(RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        PlanExerciseResponse planExerciseResponse = new PlanExerciseResponse();
        planExerciseResponse.setId(deletedPlan.getId());

        ExerciseResponse exerciseResponse = new ExerciseResponse(deletedPlan.getExercise().getId(),deletedPlan.getExercise().getName(), deletedPlan.getExercise().getMuscleGroup());
        WorkoutPlanResponse wpr = new WorkoutPlanResponse(deletedPlan.getWorkoutPlan().getName(), deletedPlan.getWorkoutPlan().getId(), deletedPlan.getWorkoutPlan().getCreatedAt(), deletedPlan.getWorkoutPlan().getUpdatedAt());

        planExerciseResponse.setExerciseResponse(exerciseResponse);
        planExerciseResponse.setWorkoutPlanResponse(wpr);

        planExerciseResponse.setTargetSets(deletedPlan.getTargetSets());
        planExerciseResponse.setTargetReps(deletedPlan.getTargetReps()  );

        Map<String, Object> response = new HashMap<>();
        response.put("plan_exercise", planExerciseResponse);
        response.put("user_id", user.getId());
        return ResponseEntity.status(200).body(response);
    }
}
