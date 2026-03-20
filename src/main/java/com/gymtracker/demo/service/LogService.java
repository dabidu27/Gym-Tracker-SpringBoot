package com.gymtracker.demo.service;


import com.gymtracker.demo.entity.Exercise;
import com.gymtracker.demo.entity.ExerciseLog;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutSession;
import com.gymtracker.demo.repository.ExerciseLogRepository;
import com.gymtracker.demo.repository.ExerciseRepository;
import com.gymtracker.demo.repository.WorkoutSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    ExerciseLogRepository exerciseLogRepository;

    @Autowired
    WorkoutSessionRepository workoutSessionRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    public ExerciseLog logSet(User user, Long exerciseId, Long sessionId, Integer setNumber, Float weight, Integer reps){

        WorkoutSession workoutSession = this.workoutSessionRepository.findByIdAndUser(sessionId, user).orElseThrow(() -> new RuntimeException("Workout session not found"));
        Exercise exercise = this.exerciseRepository.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found"));

        ExerciseLog exerciseLog = new ExerciseLog();
        exerciseLog.setWorkoutSession(workoutSession);
        exerciseLog.setExercise(exercise);
        exerciseLog.setSetNumber(setNumber);
        exerciseLog.setWeight(weight);
        exerciseLog.setReps(reps);

        return this.exerciseLogRepository.save(exerciseLog);
    }

    public ExerciseLog editSet(User user, Long logId, Long sessionId, Integer setNumber, Float weight, Integer reps) {

        ExerciseLog exerciseLog = this.exerciseLogRepository.findById(logId).orElseThrow(() -> new RuntimeException("Log not found"));

        if(!exerciseLog.getWorkoutSession().getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized");
        }

        exerciseLog.setSetNumber(setNumber);
        exerciseLog.setWeight(weight);
        exerciseLog.setReps(reps);

        return this.exerciseLogRepository.save(exerciseLog);
    }

    public ExerciseLog deleteSet(User user, Long logId, Long sessionId){

        WorkoutSession workoutSession = this.workoutSessionRepository.findByIdAndUser(sessionId, user).orElseThrow(() -> new RuntimeException("Workout session not found"));
        ExerciseLog exerciseLog = this.exerciseLogRepository.findByIdAndWorkoutSession(logId, workoutSession).orElseThrow(() -> new RuntimeException("Log not found"));

        this.exerciseLogRepository.deleteById(logId);
        return exerciseLog;
    }
}
