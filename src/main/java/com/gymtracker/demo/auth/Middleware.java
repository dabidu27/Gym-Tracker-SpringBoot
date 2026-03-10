package com.gymtracker.demo.auth;


import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class Middleware {

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
