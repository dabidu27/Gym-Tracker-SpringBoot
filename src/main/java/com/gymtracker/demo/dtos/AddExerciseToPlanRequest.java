package com.gymtracker.demo.dtos;

//user creates workout plan -> user select workout plan -> user adds an exercise from the global list of exercises
//the frontend sends the workout plan id through the url, and in the request the exerciseId (from the global exercises table) and targetSets and targetReps

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddExerciseToPlanRequest {

    //@NotBlank can only be used on Strings
    @NotNull
    private Long exerciseId;
    @NotNull
    private Integer targetSets;
    @NotNull
    private Integer targetReps;
}
