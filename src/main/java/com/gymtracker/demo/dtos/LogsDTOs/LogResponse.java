package com.gymtracker.demo.dtos.LogsDTOs;

import com.gymtracker.demo.dtos.ExerciseDTOs.ExerciseResponse;
import com.gymtracker.demo.dtos.WorkoutSessionDTOs.WorkoutSessionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogResponse {

    private Long id;
    ExerciseResponse er;
    private Integer setNumber;
    private Float weight;
    private Integer reps;
    private Long userId;
    private LocalDateTime loggedAt;
}
