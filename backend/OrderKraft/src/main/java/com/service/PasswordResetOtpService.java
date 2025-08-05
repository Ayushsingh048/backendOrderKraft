package com.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.entity.EmailDetails;
import com.entity.PasswordResetOtp;
import com.entity.User;
import com.repository.PasswordResetOtpRepository;
import com.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PasswordResetOtpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetOtpRepository otpRepository;

    public String sendPasswordResetOTP(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        //Checks if email exists or not
        if (!userOpt.isPresent()) return "User not found.";
        
        String otp = generateOTP();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        // Save or update OTP entity in Oracle DB
        PasswordResetOtp passwordResetOtp = otpRepository.findByEmail(email)
            .orElse(new PasswordResetOtp());
        passwordResetOtp.setEmail(email);
        passwordResetOtp.setOtp(otp);
        passwordResetOtp.setExpirationTime(expiry);
        otpRepository.save(passwordResetOtp);
        
        // Email body
        String emailBody = "Your OTP for password reset is: " + otp;
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(email);
        emailDetails.setSubject("Password Reset OTP");
        emailDetails.setMsgBody(emailBody);
        try {
            emailService.sendSimpleMail(emailDetails);
            return "OTP sent to your email!";
        }
      catch (Exception e) {
            return "OTP sending failed";
        }
            
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    
    @Transactional
    public boolean verifyOTP(String email, String otp) {
        Optional<PasswordResetOtp> otpEntryOpt = otpRepository.findByEmail(email);
        if (otpEntryOpt.isPresent()) {
            PasswordResetOtp otpEntry = otpEntryOpt.get();

            if (otpEntry.getOtp().equals(otp) && otpEntry.getExpirationTime().isAfter(LocalDateTime.now())) {
                otpRepository.deleteByEmail(email);
                return true;
            }
        }
        return false;
    }

}
