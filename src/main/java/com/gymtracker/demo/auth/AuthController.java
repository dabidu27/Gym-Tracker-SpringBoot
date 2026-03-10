package com.gymtracker.demo.auth;


import com.gymtracker.demo.auth.dtos.LoginRequest;
import com.gymtracker.demo.auth.dtos.RegisterRequest;
import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> postRegister(@Valid @RequestBody RegisterRequest req){

        String username = req.getUsername();
        String email = req.getEmail();
        String password = req.getPassword();
        User user = new User();
        try{
            user = authService.registerUser(username, email, password);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", username);
        response.put("email", email);

        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> postLogin(@Valid @RequestBody LoginRequest req){

        String username = req.getUsername();
        String password = req.getPassword();

        String token = "";
        try{
            token = authService.loginUser(username, password);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("access_token", token);
        return ResponseEntity.status(200).body(response);
    }

}
