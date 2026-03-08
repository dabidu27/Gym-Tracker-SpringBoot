package com.gymtracker.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Exercise and WorkoutPlan tables are in a M:M relationship, and PlanExercise is the junction table
@Entity
@Table(name = "plan_exercises")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(nullable = false)
    private Integer targetSets;

    @Column(nullable = false)
    private Integer targetReps;

}
