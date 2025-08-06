package com.controller;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.util.HashMap;

import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest ,HttpServletResponse response) {
        Optional<User> user = userService.getUserByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
        	String normalizedRole = user.get().getRole().getName().toUpperCase().replace(" ", "_");
        	String token = jwtTokenProvider.createToken(user.get().getEmail(), normalizedRole);
        
        	Cookie cookie = new Cookie("jwt",token);
        	cookie.setHttpOnly(true);
        	cookie.setSecure(false);// should be true in production
        	cookie.setMaxAge(5*60);//5 mins
        	cookie.setPath("/");
        	
        	response.addCookie(cookie);
        	
        	
        	
        	Map<String, String> res = new HashMap<>();
            res.put("message", "Login successful");
            res.put("email", user.get().getEmail());
//            response.put("token", token); // <-- Token support added
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
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

        return ResponseEntity.ok(userInfo);
    }

}
