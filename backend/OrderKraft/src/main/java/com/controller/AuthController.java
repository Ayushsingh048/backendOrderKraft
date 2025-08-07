package com.controller;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.service.EmailService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Value("${spring.mail.admin_mail}")
    private String adminEmail;
    
 // In-memory map to track login failures per user
    private Map<String, Integer> loginAttemptsMap = new ConcurrentHashMap<>();


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) throws Exception {
    	Optional<User> userOptional = userService.getUserByEmail(loginRequest.getEmail());
      
        // invalid username 
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body (Map.of("message", "Invalid credentials"));
           
        }


        User user = userOptional.get();

        // 1. Check if account is inactive
        if ("inactive".equalsIgnoreCase(user.getStatus())) {
            return ResponseEntity.status(401).body(Map.of("message", "Account is locked. Please contact admin."));
            
        }

        String email = user.getEmail();
        Map<String, String> response = new HashMap<>();

        // 2. If password matches
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            loginAttemptsMap.remove(email); // reset attempt count

            String normalizedRole = user.getRole().getName().toUpperCase().replace(" ", "_");
            String token = jwtTokenProvider.createToken(email, normalizedRole);

            response.put("message", "Login successful");
            response.put("email", email);
            response.put("username", user.getUsername());
            response.put("role", user.getRole().getName());
            response.put("token", token);  // <-- Token support added

            return ResponseEntity.ok(response);
        } else {
            // 3. Track failed attempt
            int attempts = loginAttemptsMap.getOrDefault(email, 0) + 1;
            loginAttemptsMap.put(email, attempts);

            // 4. Lock account after 5 failures
            if (attempts >= 5) {
                user.setStatus("inactive");
                userService.saveUser(user);
                loginAttemptsMap.remove(email);

                // Notify user
                emailService.sendSimpleMail(
                    email,
                    "Account Locked - OrderKraft",
                    "Your account has been locked after 5 incorrect login attempts. Please contact the admin."
                );

                // Notify admin
                emailService.sendSimpleMail(
                    adminEmail,
                    "User Account Locked - OrderKraft",
                    "User with username: " + user.getUsername() + " is locked after 5 failed login attempts."
                );

                return ResponseEntity.status(401).body(Map.of("message", "Account locked after 5 failed attempts. Contact admin."));
            }

            return ResponseEntity.status(401).body(Map.of("message", "Invalid password. Attempt " + attempts + " of 5."));
        }
    }
}
