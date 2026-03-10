package com.gymtracker.demo.auth;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    public User registerUser(String username, String email, String password){

        if(this.userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Username is already taken");
        }

        if(this.userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email is already used");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        String hashedPassword = this.passwordEncoder.encode(password);
        user.setHashedPassword(hashedPassword);

        return this.userRepository.save(user);
    }

    public String loginUser(String username, String password){

        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(username, password);
        try{
            authenticationManager.authenticate(credentials);
        }catch (AuthenticationException e){
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(username);

        return token;
    }
}
