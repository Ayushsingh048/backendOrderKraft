package com.controller;

import com.entity.User;
import com.repository.UserRepository;
import com.service.PasswordResetOtpService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class PasswordResetOtpController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
    @Autowired
    private PasswordResetOtpService forgotPasswordService;

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailRequest email) {
        String response = forgotPasswordService.sendPasswordResetOTP(email.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody EmailRequest email) {
    	System.out.println(email.getOtp());
        boolean isValid = forgotPasswordService.verifyOTP(email.getEmail(), email.getOtp());
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(400).body("Invalid OTP.");
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody EmailRequest email) {
        Optional<User> userOpt = userRepository.findByEmail(email.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOpt.get();

        String encryptedPassword = passwordEncoder.encode(email.getPassword());
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully.");
    }
}

class EmailRequest{
	private String email;
	private String newPassword;
	private String otp;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getPassword() {
		return newPassword;
	}

	public void setPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}