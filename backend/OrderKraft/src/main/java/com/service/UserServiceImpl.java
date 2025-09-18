package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dto.UserDTO;
import com.dto.PasswordResetRequest;
import com.dto.PasswordUpdateDTO;
import com.entity.Role;
import com.entity.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.entity.EmailDetails;
import com.utils.PasswordValidator;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public User createUser(UserDTO dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepo.findByName(dto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        user.setUserSession(dto.getUserSession());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        // ✅ First time login requires password reset
        user.setResetRequired(false);

        // generate random account number
        String accountNumber = (dto.getAccountNumber() != null && !dto.getAccountNumber().isEmpty())
                ? dto.getAccountNumber()
                : UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        user.setAccountNumber(accountNumber);

        User savedUser = userRepo.save(user);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(savedUser.getEmail());
        emailDetails.setSubject("Welcome to OrderKraft");
        emailDetails.setMsgBody("Dear " + dto.getUsername() + "," + "\nWe’re excited to let you know that your registration has been successfully completed!\r\n\n"
                + "Here are your login details:\n"
                + "Email: " + dto.getEmail() + "\n"
                + "Password: " + dto.getPassword() + "\n\nPlease keep this information secure and do not share it with anyone. We recommend changing your password after your first login for enhanced security.\r\n"
                + "If you have any questions or need assistance, feel free to reach out to our support team.\r\n"
                + "Welcome to OrderKraft!\r\n\n"
                + "Warm regards,\r\n"
                + "Team OK\r\n"
                + "OrderKraft");

        try {
            emailService.sendSimpleMail(emailDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsersByStatus(String status) {
        return userRepo.findByStatus(status);
    }

    @Override
    public List<User> getUsersBySession(String userSession) {
        return userRepo.findByUserSession(userSession);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User updateUserByAdmin(Long id, UserDTO dto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getStatus() != null && !dto.getStatus().trim().isEmpty()) {
            user.setStatus(dto.getStatus());
        }

        if (dto.getRoleName() != null && !dto.getRoleName().trim().isEmpty()) {
            Role role = roleRepo.findByName(dto.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }

        return userRepo.save(user);
    }

    @Override
    public User updateUserProfile(Long id, UserDTO dto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            user.setEmail(dto.getEmail());
        }

        return userRepo.save(user);
    }

    // ✅ Reset password on first login
    @Override
    public void resetPasswordOnFirstLogin(PasswordResetRequest request, String username) {
        Optional<User> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = optionalUser.get();

        // Check if old password matches
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect old password");
        }

        // ✅ Disallow same old and new password
        if (request.getOldPassword().equals(request.getNewPassword()) ||
            passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must not be the same as old password");
        }

        // Validate new password policy
        if (!PasswordValidator.isValidPassword(request.getNewPassword())) {
            throw new IllegalArgumentException("New password does not meet security requirements.");
        }

        // Encrypt and update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetRequired(true); // mark reset complete
        userRepo.save(user);
    }

    // ✅ Update password for all users (not just first login)
    @Override
    public User updatePassword(Long id, PasswordUpdateDTO dto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (dto.getNewPassword() == null || dto.getNewPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be blank");
        }

        // ✅ Disallow same new and current password
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword()) ||
            dto.getCurrentPassword().equals(dto.getNewPassword())) {
            throw new IllegalArgumentException("New password must not be the same as current password");
        }

        if (!PasswordValidator.isValidPassword(dto.getNewPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain an uppercase letter and a number.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setResetRequired(true);
        return userRepo.save(user);
    }

    // Save user (for failed login attempts, etc.)
    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public Optional<User> getUserByAccountNumber(String accountNumber) {
        return userRepo.findByAccountNumber(accountNumber);
    }
}
