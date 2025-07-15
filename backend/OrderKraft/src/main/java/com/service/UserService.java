package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.UserDTO;
import com.entity.User;

public interface UserService {
    User createUser(UserDTO dto);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id); 
    Optional<User> getUserByUsername(String username);
    List<User> getUsersByStatus(String status);
    List<User> getUsersBySession(String userSession);
    
    //Get me a user if present, and wrap it in an Optional, so I can
    // cleanly handle both present and not-found cases using .map().
}
