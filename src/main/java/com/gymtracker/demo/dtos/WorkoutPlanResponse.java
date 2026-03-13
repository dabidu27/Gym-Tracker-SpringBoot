package com.gymtracker.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//DTO used for controlling response format, to avoid returning an entire User object
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutPlanResponse {

    private String name;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
