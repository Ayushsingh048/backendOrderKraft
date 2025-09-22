package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Fetch all notifications for a specific user
    List<Notification> findByUsername(String username);

    // Fetch all notifications for a specific role
    List<Notification> findByRole(String role);
    
    // Fetches 10 latest notifications for a specific user
    List<Notification> findTop10ByUsernameOrderByCreatedAtDesc(String username);
}