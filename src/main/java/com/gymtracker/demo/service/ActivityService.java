package com.gymtracker.demo.service;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.repository.WorkoutSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {

    @Autowired
    WorkoutSessionRepository workoutSessionRepository;

    public Map<String, Long> getActivity(User user){

        List<Object[]> activity = this.workoutSessionRepository.findActivityByUser(user, LocalDateTime.now().minusYears(1));
        Map<String, Long> activityMap = new HashMap<>();

        //each element of the activity List is an Object[] (array of Object)
        //each Object[] corresponds to a row returned by the query
        //row[0] = workout date
        //row[1] = count of workouts on that date
        //the .stream().map() version below will not work (it will not populate activity) - map is for modifying the elements of a collection, not for side effects like putting the elements into another collection
//       activity.stream().map(row -> activityMap.put(row[0].toString(), ((Number)row[1]).longValue()));
        //user .forEach()
        activity.forEach(row -> activityMap.put(row[0].toString(), ((Number)row[1]).longValue()));
        return activityMap;
    }
}
