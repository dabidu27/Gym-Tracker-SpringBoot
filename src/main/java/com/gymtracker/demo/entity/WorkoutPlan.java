package com.gymtracker.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "workout_plans")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //if we make this field unique here, it will be unique across the whole db => 2 users cannot have the same name for their workout plans
    //we will enforce name uniquenss for each other's workout plans by checking if a workout plan with the same
    //already exists in the table when a createWorkoutPlan request comes in
    @Column(nullable = false)
    private String name;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //@PrePersist => run this when the object is saved for the first time
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); //updatedAt also has to be updated here
    }

    //@PreUpdate => run this whenever an object is updated
    @PreUpdate
    protected void onUpdate(){this.updatedAt = LocalDateTime.now();}
}
