package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dto.UserDTO;
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

        User savedUser = userRepo.save(user);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(savedUser.getEmail());
        emailDetails.setSubject("Welcome to OrderKraft");
        emailDetails.setMsgBody("Dear "+dto.getUsername()+"," +"\nWe’re excited to let you know that your registration has been successfully completed!\r\n\n"
        		+ "Here are your login details:\n"
                + "Email: " + dto.getEmail() + "\n"
                + "Password: " + dto.getPassword()+"\n\nPlease keep this information secure and do not share it with anyone. We recommend changing your password after your first login for enhanced security.\r\n"
                		+ "If you have any questions or need assistance, feel free to reach out to our support team.\r\n"
                		+ "Welcome to OrderKraft!\r\n\n"
                		+ "Warm regards,\r\n"
                		+ "Team OK\r\n"
                		+ "OrderKraft");
        
        
        
//        String htmlBody = """
//        	    <html>
//        	    <body style="font-family: Arial, sans-serif; line-height: 1.6;">
//        	        <h2 style="color: #4CAF50;">Welcome to OrderKraft, %s!</h2>
//        	        <p>We're excited to have you on board. Your account has been successfully created with the following credentials:</p>
//
//        	        <table style="border-collapse: collapse; width: 100%%; max-width: 500px;">
//        	            <tr>
//        	                <td style="padding: 8px; font-weight: bold;">Email:</td>
//        	                <td style="padding: 8px;">%s</td>
//        	            </tr>
//        	            <tr>
//        	                <td style="padding: 8px; font-weight: bold;">Password:</td>
//        	                <td style="padding: 8px;">%s</td>
//        	            </tr>
//        	            <tr>
//        	                <td style="padding: 8px; font-weight: bold;">Role:</td>
//        	                <td style="padding: 8px;">%s</td>
//        	            </tr>
//        	        </table>
//
//        	        <p style="margin-top: 20px;">
//        	            You can now login to the <strong>OrderKraft Portal</strong> and start exploring our features designed to simplify your business operations.
//        	        </p>
//
//        	        <p>If you have any questions or need help, feel free to reply to this email or contact our support team.</p>
//
//        	        <p>Best regards,<br><strong>The OrderKraft Team</strong></p>
//        	    </body>
//        	    </html>
//        	    """.formatted(dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getRoleName());
//        	    emailDetails.setMsgBody(htmlBody);

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
            throw new IllegalArgumentException("Password must be at least 8 characters long, must contain an uppercase letter and a number.");
        }
        
        // Check if new password is same as current
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must not be the same as current password");
        }

	    // Update password if matched
	    if (dto.getNewPassword() == null || dto.getNewPassword().trim().isEmpty()) {
	        throw new IllegalArgumentException("New password cannot be blank");
	    }
	    

	    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
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

