package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.UserDTO;
import com.dto.PasswordUpdateDTO;
import com.entity.Role;
import com.entity.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public User createUser(UserDTO dto) {
        Role role = roleRepo.findByName(dto.getRoleName())
                            .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        user.setUserSession(dto.getUserSession());
        user.setRole(role);
        user.setPassword(dto.getPassword());

        return userRepo.save(user);
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
}
