package com.gymtracker.demo.controller;


import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.dtos.ExerciseDTOs.ExerciseResponse;
import com.gymtracker.demo.dtos.LogsDTOs.EditLogRequest;
import com.gymtracker.demo.dtos.LogsDTOs.LogResponse;
import com.gymtracker.demo.dtos.LogsDTOs.LogSetRequest;
import com.gymtracker.demo.dtos.WorkoutSessionDTOs.WorkoutSessionResponse;
import com.gymtracker.demo.entity.Exercise;
import com.gymtracker.demo.entity.ExerciseLog;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutSession;
import com.gymtracker.demo.repository.ExerciseRepository;
import com.gymtracker.demo.repository.WorkoutSessionRepository;
import com.gymtracker.demo.service.LogService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
public class LogController {

    @Autowired
    LogService logService;

    @Autowired
    Middleware middleware;


    @PostMapping("/{sessionId}/logs")
    public ResponseEntity<Object> logSet(@PathVariable Long sessionId, @Valid @RequestBody LogSetRequest req){

        User user = this.middleware.getCurrentUser();
        Long exerciseId = req.getExerciseId();
        Integer setNumber = req.getSetNumber();
        Float weight = req.getWeight();
        Integer reps = req.getReps();

        ExerciseLog exerciseLog = new ExerciseLog();
        try{
            exerciseLog = this.logService.logSet(user, exerciseId, sessionId, setNumber, weight, reps);
        }catch (RuntimeException e){
            Map<String, String> response= new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        LogResponse logResponse = new LogResponse();
        logResponse.setId(exerciseLog.getId());

        logResponse.setEr(ExerciseResponse.makeResponse(exerciseLog.getExercise()));

        logResponse.setSetNumber(exerciseLog.getSetNumber());
        logResponse.setWeight(exerciseLog.getWeight());
        logResponse.setReps(exerciseLog.getReps());
        logResponse.setUserId(user.getId());
        logResponse.setLoggedAt(exerciseLog.getLoggedAt());

        return ResponseEntity.status(201).body(logResponse);

    }

    @PatchMapping("/{sessionId}/logs/{logId}")
    public ResponseEntity<Object> editSet(@PathVariable Long sessionId, @PathVariable Long logId, @Valid @RequestBody EditLogRequest req){

        User user = this.middleware.getCurrentUser();
        Integer setNumber = req.getSetNumber();
        Float weight = req.getWeight();
        Integer reps = req.getReps();

        ExerciseLog exerciseLog = new ExerciseLog();
        try{
            exerciseLog = this.logService.editSet(user, logId, sessionId, setNumber, weight, reps);
        } catch (RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        LogResponse logResponse = new LogResponse();
        logResponse.setId(exerciseLog.getId());

        logResponse.setEr(ExerciseResponse.makeResponse(exerciseLog.getExercise()));

        logResponse.setSetNumber(exerciseLog.getSetNumber());
        logResponse.setWeight(exerciseLog.getWeight());
        logResponse.setReps(exerciseLog.getReps());
        logResponse.setUserId(user.getId());
        logResponse.setLoggedAt(exerciseLog.getLoggedAt());

        return ResponseEntity.status(200).body(logResponse);
    }

    @DeleteMapping("/{sessionId}/logs/{logId}")
    public ResponseEntity<Object> deleteSet(@PathVariable Long logId, @PathVariable Long sessionId){

        User user = this.middleware.getCurrentUser();
        ExerciseLog exerciseLog = new ExerciseLog();
        try{
            exerciseLog = this.logService.deleteSet(user, logId, sessionId);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        LogResponse logResponse = new LogResponse();
        logResponse.setId(exerciseLog.getId());
        logResponse.setEr(ExerciseResponse.makeResponse(exerciseLog.getExercise()));

        logResponse.setSetNumber(exerciseLog.getSetNumber());
        logResponse.setWeight(exerciseLog.getWeight());
        logResponse.setReps(exerciseLog.getReps());

        logResponse.setLoggedAt(exerciseLog.getLoggedAt());

        logResponse.setUserId(user.getId());

        return ResponseEntity.status(200).body(logResponse);
    }

}
