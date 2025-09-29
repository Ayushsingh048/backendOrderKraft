package com.service;

import java.util.List;

import com.entity.Notification;


public interface NotificationService {
	
	
	// Create for single user
    Notification createNotification(String title, String message, String username);
	
    // Create for a whole role
    void createNotificationForRole(String title, String message, String roleName);
	
	//Get user notifications
	List<Notification> getByUsername(String username);
	
	// Mark all as read
    void markAllAsRead(String email);
    
    List<Notification> getLast10ByUsername(String username);
    
    void deleteNotification(Long id);
}
