package com.gymtracker.demo.dtos.PlanExerciseDTOs;

import com.gymtracker.demo.dtos.ExerciseDTOs.ExerciseResponse;
import com.gymtracker.demo.dtos.WorkoutPlanDTOs.WorkoutPlanResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanExerciseResponse {

    private Long id;
    private ExerciseResponse exerciseResponse;
    private WorkoutPlanResponse workoutPlanResponse;
    private Integer targetSets;
    private Integer targetReps;
}
