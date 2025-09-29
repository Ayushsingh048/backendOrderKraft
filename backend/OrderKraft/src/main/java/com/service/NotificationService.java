package com.service;

import java.util.List;

import com.entity.Notification;


public interface NotificationService {
	
	
	// Create for single user
    public Notification createNotification(String title, String message, String username);
	
    // Create for a whole role
    public Notification createNotificationForRole(String title, String message, String roleName);
    
	// Get role notifications
	public List<Notification> getByRole(String role);
	
	//Get user notifications
	public List<Notification> getByUsername(String username);
	
	//Get use notifications both role and username
	List<Notification> getUserAndRoleNotifications(String username);
	
	// Mark all as read
    public void markAllAsRead(String email);
    
    public List<Notification> getLast10ByUsername(String username);
    
    public void deleteNotification(Long id);
}
