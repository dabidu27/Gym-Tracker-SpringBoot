package com.gymtracker.demo.dtos.LogsDTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogSetRequest {

    @NotNull
    private Long exerciseId;

    //is computed by the frontend
    @NotNull
    @Min(1)
    private Integer setNumber;

    @NotNull
    @Min(0)
    private Float weight;

    @NotNull
    @Min(0)
    private Integer reps;


}
