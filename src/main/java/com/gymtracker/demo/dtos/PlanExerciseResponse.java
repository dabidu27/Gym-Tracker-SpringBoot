package com.gymtracker.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanExerciseResponse {

    private Long id;
    private Long exerciseId;
    private Long workoutPlanId;
    private Integer targetSets;
    private Integer targetReps;
}
