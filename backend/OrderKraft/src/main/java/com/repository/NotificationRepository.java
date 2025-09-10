package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Fetch all notifications for a specific user
    List<Notification> findByUsername(String username);

    // Fetch all notifications for a specific role
    List<Notification> findByRole(String role);

    // Optionally fetch unread notifications for a user
    List<Notification> findByUsernameAndStatus(String username, String status);

    // Optionally fetch unread notifications for a role
    List<Notification> findByRoleAndStatus(String role, String status);
}