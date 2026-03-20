package com.gymtracker.demo.controller;

import com.gymtracker.demo.auth.Middleware;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/users/me/activity")
public class ActivityController{

    @Autowired
    ActivityService activityService;

    @Autowired
    Middleware middleware;

    @GetMapping
    public ResponseEntity<Object> getActivity(){

        User user = this.middleware.getCurrentUser();
        Map<String, Long> response = this.activityService.getActivity(user);
        return ResponseEntity.status(200).body(response);

    }
}
