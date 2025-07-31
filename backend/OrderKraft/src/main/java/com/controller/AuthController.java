package com.controller;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginRequestDTO;
import com.entity.User;
import com.security.JwtTokenProvider;
import com.service.UserService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<User> user = userService.getUserByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
        	String token = jwtTokenProvider.createToken(user.get().getEmail(), user.get().getRole().getName());
        	Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("email", user.get().getEmail());
<<<<<<< HEAD
            response.put("token", token); // <-- Token support added
=======
            response.put("username",user.get().getUsername()); // for welcome message
            response.put("role", user.get().getRole().getName());// for displaying corresponding dashboard
            response.put("token", "dummy-token"); // <-- Add token support later
>>>>>>> a7e5ae85882b4f19ec34828851e39b37163cd463
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
