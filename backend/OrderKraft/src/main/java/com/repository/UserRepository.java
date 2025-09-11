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
	boolean existsByUsername(String username);

	List<User> findByRoleName(String roleName);
}