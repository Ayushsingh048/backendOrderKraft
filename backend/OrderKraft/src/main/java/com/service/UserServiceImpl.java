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
//        if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
//            throw new RuntimeException("Username already exists");
//        }

        Role role = roleRepo.findByName(dto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        user.setUserSession(dto.getUserSession());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setResetRequired(false);
        User savedUser = userRepo.save(user);
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(savedUser.getEmail());
        emailDetails.setSubject("Welcome to OrderKraft");
        emailDetails.setMsgBody("Your account has been created and your registered email and password is:\n\n"
                + "Email: " + dto.getEmail() + "\n"
                + "Password: " + dto.getPassword());

        try {
			emailService.sendSimpleMail(emailDetails);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

    // ✅ Updating password with policy enforcement
   
//    @Override
//    public void resetPasswordOnFirstLogin(PasswordResetRequest request, String email) {
//        Optional<User> optionalUser = userRepo.findByEmail(email);
//        if (optionalUser.isEmpty()) {
//            throw new RuntimeException("User not found.");
//        }
//
//        User user = optionalUser.get();
//
//        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("Old password is incorrect.");
//        }
//
//        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
//            throw new IllegalArgumentException("New password and confirm password do not match.");
//        }
//
//        if (!PasswordValidator.isValidPassword(request.getNewPassword())) {
//            throw new IllegalArgumentException("Password must be at least 8 characters long, contain an uppercase letter and a number.");
//        }
//
//        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//        user.setResetRequired(true);
//        userRepo.save(user);
//    }

    
   // function for resetting password on first login of user 
    
    @Override
    public void resetPasswordOnFirstLogin(PasswordResetRequest request, String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
      
        System.out.println(email);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = optionalUser.get();
        System.out.println("Entered old: " + request.getOldPassword());
        System.out.println("DB hashed pwd: " + user.getPassword());
        System.out.println("Match: " + passwordEncoder.matches(request.getOldPassword(), user.getPassword()));

        // Check if old password matches
        if (!passwordEncoder.matches(request.getOldPassword(),user.getPassword())) {
            throw new IllegalArgumentException("Incorrect old password");
        }

        // Validate new password
        if (!PasswordValidator.isValidPassword(request.getNewPassword())) {
            throw new IllegalArgumentException("New password does not meet security requirements.");
        }

        // Encrypt and update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetRequired(true); // ✅ Mark password reset complete
        userRepo.save(user);
    }
    
//updating password for all kinds of users 
    
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

        if (!PasswordValidator.isValidPassword(dto.getNewPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain an uppercase letter and a number.");
        }

	    // Update password if matched
	    if (dto.getNewPassword() == null || dto.getNewPassword().trim().isEmpty()) {
	        throw new IllegalArgumentException("New password cannot be blank");
	    }

	    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
	    user.setResetRequired(true);
	    return userRepo.save(user);
	}
	
	
// for updating status to inactive (for failed login attempts)
	@Override
	public void saveUser(User user) {
	    userRepo.save(user);
	}
	

// checks if an email already exists or not
    @Override
    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }
}

