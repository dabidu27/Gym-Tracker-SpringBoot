package com.gymtracker.demo.dtos.PlanExerciseDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPlanExercise {

    @NotNull
    private Integer sets;
    @NotNull
    private Integer reps;
}
