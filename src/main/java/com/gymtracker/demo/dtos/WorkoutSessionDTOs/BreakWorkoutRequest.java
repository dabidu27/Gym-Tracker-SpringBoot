package com.gymtracker.demo.dtos.WorkoutSessionDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreakWorkoutRequest {

    @NotNull
    private int totalBreakSeconds;
}
