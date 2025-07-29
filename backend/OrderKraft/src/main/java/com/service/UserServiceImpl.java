package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.UserDTO;
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
//    	 if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
//    	        throw new RuntimeException("Email already exists");
//    	    }
//    	    if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
//    	        throw new RuntimeException("Username already exists");
//    	    }
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
    
    
}
