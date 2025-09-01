package com.controller;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.util.HashMap;

import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CustomUserDetails;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest,HttpServletResponse response ) throws Exception {

        Optional<User> userOptional = userService.getUserByEmail(loginRequest.getEmail());
      System.out.println(loginRequest.getEmail());
        // invalid username 
        if (userOptional.isEmpty()) {
        	System.out.println("user not found"+ userOptional);
            return ResponseEntity.status(401).body (Map.of("message", "Invalid credentials"));
           
        }


        User user = userOptional.get();

        // 1. Check if account is inactive
        if ("inactive".equalsIgnoreCase(user.getStatus())) {
            return ResponseEntity.status(401).body(Map.of("message", "Account is locked. Please contact admin."));
            
        }

        String email = user.getEmail();
        // Map<String, String> response = new HashMap<>();

        // 2. If password matches
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            loginAttemptsMap.remove(email); // reset attempt count

            String normalizedRole = user.getRole().getName().toUpperCase().replace(" ", "_");
        	String token = jwtTokenProvider.createToken(user.getEmail(), normalizedRole);
        
        	Cookie cookie = new Cookie("jwt",token);
        	cookie.setHttpOnly(true);
        	cookie.setSecure(false);// should be true in production
        	cookie.setMaxAge(60*60);//5 mins
        	cookie.setPath("/");
        	
        	response.addCookie(cookie);
        	
        	
        	
        	Map<String, String> res = new HashMap<>();
            res.put("message", "Login successful");
            res.put("email", user.getEmail());
//            response.put("token", token); // <-- Token support added
            return ResponseEntity.ok(res);
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
    
    
    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
    	System.out.println("Received request to /user-info");
        System.out.println("Authentication object: " + authentication);
        
        if (authentication == null || !authentication.isAuthenticated()) {
        	 System.out.println("Not authenticated or authentication is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }

        // Ensure the principal is of the expected type
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
        	 System.out.println("Invalid authentication principal type: " + authentication.getPrincipal().getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Invalid authentication principal");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser(); // Get full user entity

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("email", user.getEmail());
        userInfo.put("status", user.getStatus());
        userInfo.put("role", user.getRole().getName());
        userInfo.put("resetRequired", user.getResetRequired());

        return ResponseEntity.ok(userInfo);
    }

}
