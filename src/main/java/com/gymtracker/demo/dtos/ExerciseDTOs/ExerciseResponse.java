package com.gymtracker.demo.dtos.ExerciseDTOs;

import com.gymtracker.demo.entity.Exercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResponse {

    private Long id;
    private String name;
    private String muscleGroup;

    public static ExerciseResponse makeResponse(Exercise exercise){

        ExerciseResponse exerciseResponse = new ExerciseResponse(exercise.getId(), exercise.getName(), exercise.getMuscleGroup());
        return exerciseResponse;
    }
}
