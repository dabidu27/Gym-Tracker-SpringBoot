package com.gymtracker.demo.dtos.WorkoutSessionDTOs;

import com.gymtracker.demo.dtos.WorkoutPlanDTOs.WorkoutPlanResponse;
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
}
