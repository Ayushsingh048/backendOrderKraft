package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.dto.UserDTO;
import com.dto.PasswordResetRequest;
import com.dto.PasswordUpdateDTO;
import com.entity.User;

public interface UserService {
    User createUser(UserDTO dto);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id); 
    Optional<User> getUserByUsername(String username);
    List<User> getUsersByStatus(String status);
    List<User> getUsersBySession(String userSession);
    Optional<User> getUserByEmail(String email);
    
    // for saving the status field from active to inactive (login failed attempts 
    void saveUser(User user);


    
    // Update all except password,usersession
    User updateUserByAdmin(Long id, UserDTO dto);  
    
 // Update only username, email
    User updateUserProfile(Long id, UserDTO dto);     

 // com.service.UserService.java
    User updatePassword(Long id, PasswordUpdateDTO dto);


    

    boolean emailExists(String email);

    //Get me a user if present, and wrap it in an Optional, so I can
    // cleanly handle both present and not-found cases using .map().
    void resetPasswordOnFirstLogin(PasswordResetRequest request, String email);
}
