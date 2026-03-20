package com.gymtracker.demo.dtos.WorkoutSessionDTOs;

import com.gymtracker.demo.dtos.WorkoutPlanDTOs.WorkoutPlanResponse;
import com.gymtracker.demo.entity.WorkoutSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionResponse {

    private Long id;
    private Long userId;
    private WorkoutPlanResponse wpr;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Integer totalBreakSeconds;

    public static WorkoutSessionResponse makeResponse(WorkoutSession ws){

        WorkoutSessionResponse wsr = new WorkoutSessionResponse();
        wsr.setId(ws.getId());
        wsr.setUserId(ws.getUser().getId());
        WorkoutPlanResponse wpr = new WorkoutPlanResponse(ws.getWorkoutPlan().getName(), ws.getWorkoutPlan().getId(), ws.getWorkoutPlan().getCreatedAt(), ws.getWorkoutPlan().getUpdatedAt());
        wsr.setWpr(wpr);
        wsr.setEndedAt(ws.getEndedAt());
        wsr.setStartedAt(ws.getStartedAt());
        wsr.setTotalBreakSeconds(ws.getTotalBreakSeconds());

        return wsr;
    }
}
