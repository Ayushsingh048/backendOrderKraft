package com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginRequestDTO;
import com.entity.User;
import com.service.UserService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<User> user = userService.getUserByEmail(loginRequest.getEmail());
        if (user != null && user.get().getPassword().equals(loginRequest.getPassword())) {
        	System.out.println("login");
//        	return ResponseEntity.ok("Login successful");
        	return ResponseEntity.ok().body(new LoginRequestDTO("Login successful", user.get().getEmail()));
            
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
