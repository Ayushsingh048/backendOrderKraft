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
    private EmailService emailService; // Inject EmailService

    @Override
    public User createUser(UserDTO dto) {
        // Uncomment if you want to enforce unique email/username
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

        User savedUser = userRepo.save(user);

        // Prepare and send email
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

        emailService.sendSimpleMail(emailDetails);

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
	    // Fetch the user from the database by ID
	    User user = userRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // Update username if it's provided and not blank
	    if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
	        user.setUsername(dto.getUsername());
	    }

	    // Update email if it's provided and not blank
	    if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
	        user.setEmail(dto.getEmail());
	    }

	    // Update status if it's provided and not blank
	    if (dto.getStatus() != null && !dto.getStatus().trim().isEmpty()) {
	        user.setStatus(dto.getStatus());
	    }

	  
	    // Update role if roleName is provided and not blank
	    if (dto.getRoleName() != null && !dto.getRoleName().trim().isEmpty()) {
	        // Fetch the Role entity using roleName
	        Role role = roleRepo.findByName(dto.getRoleName())
	                .orElseThrow(() -> new RuntimeException("Role not found"));
	        user.setRole(role);
	    }

	    

	    // Save and return the updated user entity
	    return userRepo.save(user);
	}


	@Override
	public User updateUserProfile(Long id, UserDTO dto) {
	    // Fetch the user from the database by ID
	    User user = userRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // Update username if it's provided and not blank
	    if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
	        user.setUsername(dto.getUsername());
	    }

	    // Update email if it's provided and not blank
	    if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
	        user.setEmail(dto.getEmail());
	    }

	    // Save and return the updated user entity
	    return userRepo.save(user);
	}

// updating password 
	
	@Override
	public User updatePassword(Long id, PasswordUpdateDTO dto) {
	    // Fetch user from DB
	    User user = userRepo.findById(id)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    // Compare current password with DB password
	    if (!user.getPassword().equals(dto.getCurrentPassword())) {
	        throw new IllegalArgumentException("Current password is incorrect");
	    }

	    // Update password if matched
	    if (dto.getNewPassword() == null || dto.getNewPassword().trim().isEmpty()) {
	        throw new IllegalArgumentException("New password cannot be blank");
	    }

	    user.setPassword(dto.getNewPassword());
	    return userRepo.save(user);
	}

    @Override
    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }

}
