package com.gymtracker.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "workout_sessions")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;

    @Column(updatable = false, nullable = false)
    private LocalDateTime startedAt;

    @PrePersist
    protected void onCreate(){
        this.startedAt = LocalDateTime.now();
    }

    //@Column(nullable = true) - nullable = true is the default, so this is redundant
    private LocalDateTime endedAt;

    @Column(columnDefinition = "integer default 0")
    private Integer totalBreakSeconds;

}
