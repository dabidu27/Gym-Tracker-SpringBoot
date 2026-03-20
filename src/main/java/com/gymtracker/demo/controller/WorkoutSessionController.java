package com.gymtracker.demo.controller;


import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.dtos.ExerciseDTOs.ExerciseResponse;
import com.gymtracker.demo.dtos.WorkoutPlanDTOs.WorkoutPlanResponse;
import com.gymtracker.demo.dtos.WorkoutSessionDTOs.BreakWorkoutRequest;
import com.gymtracker.demo.dtos.WorkoutSessionDTOs.StartWorkoutRequest;
import com.gymtracker.demo.dtos.WorkoutSessionDTOs.WorkoutSessionResponse;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutSession;
import com.gymtracker.demo.service.WorkoutSessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//3 endpoints
//1. start workout session - session is saved to the db, with endedAt = null => the session is still running
                            //-workoutPlanId needed in the req body
//2. end workout session - update the entry in the db and set endedAt to the time the request is sent
//3. take break - user hits break on the frontend -> frontend start a timer -> user hits resume -> frontend ends timer and sends to backend how much was the break in seconds

@RestController
@RequestMapping("/api/sessions")
public class WorkoutSessionController {

    @Autowired
    WorkoutSessionService workoutSessionService;
    @Autowired
    Middleware middleware;

    @PostMapping("/start")
    public ResponseEntity<Object> startWorkoutSession(@Valid @RequestBody StartWorkoutRequest req){

        User user = this.middleware.getCurrentUser();
        Long workoutPlanId = req.getWorkoutPlanId();

        WorkoutSession workoutSession = new WorkoutSession();
        try{
            workoutSession = this.workoutSessionService.startWorkoutSession(workoutPlanId, user);
        }catch (RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        WorkoutSessionResponse wsr = new WorkoutSessionResponse();
        wsr.setId(workoutSession.getId());

        wsr.setUserId(user.getId());

        WorkoutPlanResponse wpr = new WorkoutPlanResponse(workoutSession.getWorkoutPlan().getName(), workoutSession.getWorkoutPlan().getId(), workoutSession.getWorkoutPlan().getCreatedAt(), workoutSession.getWorkoutPlan().getUpdatedAt());
        wsr.setWpr(wpr);

        wsr.setStartedAt(workoutSession.getStartedAt());
        wsr.setEndedAt(workoutSession.getEndedAt());
        wsr.setTotalBreakSeconds(workoutSession.getTotalBreakSeconds());

        return ResponseEntity.status(201).body(wsr);
    }

    //Patch vs Put request:
    //Both are for editing an entry in the db, but patch changes only 1 or some attributes, put changes them all
    @PatchMapping("/{workoutSessionId}/end")
    public ResponseEntity<Object> endWorkoutSession(@PathVariable Long workoutSessionId){

        User user = this.middleware.getCurrentUser();
        WorkoutSession endedWorkoutSession = new WorkoutSession();
        try{
            endedWorkoutSession = this.workoutSessionService.endWorkoutSession(workoutSessionId, user);
        }catch(RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        WorkoutSessionResponse wsr = new WorkoutSessionResponse();
        wsr.setId(endedWorkoutSession.getId());
        wsr.setUserId(user.getId());

        WorkoutPlanResponse wpr = new WorkoutPlanResponse(endedWorkoutSession.getWorkoutPlan().getName(), endedWorkoutSession.getWorkoutPlan().getId(), endedWorkoutSession.getWorkoutPlan().getCreatedAt(), endedWorkoutSession.getWorkoutPlan().getUpdatedAt());
        wsr.setWpr(wpr);

        wsr.setEndedAt(endedWorkoutSession.getEndedAt());
        wsr.setStartedAt(endedWorkoutSession.getStartedAt());
        wsr.setTotalBreakSeconds(endedWorkoutSession.getTotalBreakSeconds());

        return ResponseEntity.status(200).body(wsr);

    }

    @PatchMapping("/{workoutSessionId}/break")
    public ResponseEntity<Object> breakWorkoutSession(@PathVariable Long workoutSessionId, @Valid @RequestBody BreakWorkoutRequest req){

        User user = this.middleware.getCurrentUser();
        int totalBreakSeconds = req.getTotalBreakSeconds();
        WorkoutSession ws = new WorkoutSession();
        try{
            ws = this.workoutSessionService.updateBreak(workoutSessionId, user, totalBreakSeconds);
        } catch (RuntimeException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        WorkoutSessionResponse wsr = new WorkoutSessionResponse();
        wsr.setId(ws.getId());
        wsr.setUserId(user.getId());

        WorkoutPlanResponse wpr = new WorkoutPlanResponse(ws.getWorkoutPlan().getName(), ws.getWorkoutPlan().getId(), ws.getWorkoutPlan().getCreatedAt(), ws.getWorkoutPlan().getUpdatedAt());
        wsr.setWpr(wpr);

        wsr.setStartedAt(ws.getStartedAt());
        wsr.setEndedAt(ws.getEndedAt());
        wsr.setTotalBreakSeconds(ws.getTotalBreakSeconds());

        return ResponseEntity.status(200).body(wsr);
    }

}
