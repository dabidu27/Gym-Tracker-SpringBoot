package com.gymtracker.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exercise_logs")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_session_id")
    private WorkoutSession workoutSession;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Integer setNumber;
    private Float weight;
    private Integer reps;

    @Column(updatable = false)
    private LocalDateTime loggedAt;

    @PrePersist
    protected void onCreate(){
        this.loggedAt = LocalDateTime.now();
    }
}
