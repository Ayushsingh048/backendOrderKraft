package com.service;

import java.util.List;

import com.entity.Notification;


public interface NotificationService {
	
	
	// Create for single user
    public Notification createNotification(String title, String message, String username);
	
    // Create for a whole role
    public void createNotificationForRole(String title, String message, String roleName);
    
	// Get role notifications
	public List<Notification> getByRole(String role);
	
	//Get user notifications
	public List<Notification> getByUsername(String username);
	
	// Mark all as read
    public void markAllAsRead(String username);
    
    public List<Notification> getLast10ByUsername(String username);
    
    public void deleteNotification(Long id);
}
