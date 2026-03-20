package com.gymtracker.demo.dtos.LogsDTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditLogRequest {

    //only these fields should be editable by the user
    //no exerciseId. if the user logs the wrong exercise, they should delete it

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
