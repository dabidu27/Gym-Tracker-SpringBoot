package com.gymtracker.demo.service;

import com.gymtracker.demo.entity.Exercise;
import com.gymtracker.demo.entity.PlanExercise;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutPlan;
import com.gymtracker.demo.repository.ExerciseRepository;
import com.gymtracker.demo.repository.PlanExerciseRepository;
import com.gymtracker.demo.repository.WorkoutPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.EscapedErrors;

import java.util.List;

@Service
public class PlanExerciseService {

    @Autowired
    PlanExerciseRepository planExerciseRepository;
    @Autowired
    WorkoutPlanRepository workoutPlanRepository;
    @Autowired
    ExerciseRepository exerciseRepository;

    public PlanExercise addExerciseToPlan(User user, Long workoutPlanId, Long exerciseId, Integer targetSets, Integer targetReps){

//        //check if the workoutplan exists and belongs to the user
//        if(!this.workoutPlanRepository.findByIdAndUser(workoutPlanId, user).isPresent()){
//            throw new RuntimeException("Workout plan not found");
//        }

        //1. get the workout plan - in this way we also check if the workout plan exists and it belongs to the user
        WorkoutPlan wp = this.workoutPlanRepository.findByIdAndUser(workoutPlanId, user).orElseThrow(() -> new RuntimeException("Workout plan not found"));

        //check if the workout plan does not already contain this exercise
        if(this.planExerciseRepository.findByWorkoutPlanIdAndExerciseId(workoutPlanId, exerciseId).isPresent()){
            throw new RuntimeException("Exercise already added to the plan");
        }

        //if all checks pass, add exercise to plan

        //2. Get the exercise
        Exercise exercise = this.exerciseRepository.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found"));
        //3. create PlanExercise object
        PlanExercise planExercise = new PlanExercise();
        planExercise.setExercise(exercise);
        planExercise.setWorkoutPlan(wp);
        planExercise.setTargetSets(targetSets);
        planExercise.setTargetReps(targetReps);

        //4. save the object
        return this.planExerciseRepository.save(planExercise);
    }

    public List<PlanExercise> getAllExercises(Long workoutPlanId, User user){

        WorkoutPlan wp = this.workoutPlanRepository.findByIdAndUser(workoutPlanId, user).orElseThrow(() -> new RuntimeException("Workout plan not found"));
        List<PlanExercise> exercisesOfPlan = this.planExerciseRepository.findByWorkoutPlanId(workoutPlanId);
        return exercisesOfPlan;
    }

    public PlanExercise editSetsAndReps(Long workoutPlanId, Long planExerciseId, User user , Integer newSets, Integer newReps){

        WorkoutPlan wp = this.workoutPlanRepository.findByIdAndUser(workoutPlanId, user).orElseThrow(() -> new RuntimeException("Workout plan not found"));
        //find the plan exercise directly by its id (not by the global exercise id)
        PlanExercise planExercise = this.planExerciseRepository.findById(planExerciseId).orElseThrow(() -> new RuntimeException("Exercise not found in this workout plan"));
        planExercise.setTargetSets(newSets);
        planExercise.setTargetReps(newReps);
        return this.planExerciseRepository.save(planExercise);
    }

    @Transactional
    public PlanExercise deleteExercise(Long workoutPlanId, Long planExerciseId, User user){

        WorkoutPlan wp = this.workoutPlanRepository.findByIdAndUser(workoutPlanId, user).orElseThrow(() -> new RuntimeException("Workout plan not found"));
        PlanExercise planExercise = this.planExerciseRepository.findById(planExerciseId).orElseThrow(() -> new RuntimeException("Exercise not found in this workout plan"));
        this.planExerciseRepository.delete(planExercise);
        return planExercise;
    }
}
