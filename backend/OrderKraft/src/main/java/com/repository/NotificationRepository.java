package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Notification;
import com.entity.Role;
import com.entity.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Fetch all notifications for a specific user
    List<Notification> findByUser(User user);
    
    // Fetches 10 latest notifications for a specific user
    List<Notification> findTop10ByUserOrderByCreatedAtDesc(User user);

	List<Notification> findTop5ByUserOrderByCreatedAtDesc(User user);
}