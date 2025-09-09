package com.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByStatus(String status);
    List<User> findByUserSession(String userSession);
    Optional<User> findByEmail(String email);
    Optional<User> findByAccountNumber(String accountNumber);
	
	boolean existsByEmail(String email);
<<<<<<< HEAD
	List<User> findByRoleName(String roleName);
=======
	
	
>>>>>>> 9491141110094aeb01a20ce104b1bdd83473572e

	
}