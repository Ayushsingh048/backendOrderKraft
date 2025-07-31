package com.service;

import com.entity.EmailDetails;
import com.entity.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ForgotPasswordService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public String sendPasswordResetOTP(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String otp = generateOTP();
            storeOTP(user.getEmail(), otp); // Store OTP in Ehcache

            String emailBody = "Your OTP for password reset is: " + otp;

            emailService.sendSimpleMail(new EmailDetails(user.getEmail(), emailBody, "Password Reset OTP", null));
            return "OTP sent to your email.";
        } else {
            return "User not found.";
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    @Cacheable(value = "otpCache", key = "#email")
    public String storeOTP(String email, String otp) {
        return otp;
    }

    @CacheEvict(value = "otpCache", key = "#email")
    public void removeOTP(String email) {
        // This method is used to remove the OTP from the cache
    }

    public boolean verifyOTP(String email, String otp) {
        String storedOtp = storeOTP(email, null); // Retrieve OTP from Ehcache
        if (storedOtp != null && storedOtp.equals(otp)) {
            removeOTP(email); // Remove OTP after successful verification
            return true;
        }
        return false;
    }
}