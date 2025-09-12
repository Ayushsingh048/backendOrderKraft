package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.entity.Notification;
import com.entity.User;
import com.repository.UserRepository;
import com.service.NotificationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @Autowired
    private UserRepository userRepo;

    // Get all user notifications
    @GetMapping("/user")
    public ResponseEntity<List<Notification>> getUserNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Notification: "+username);
        return ResponseEntity.ok(service.getLast10ByUsername(username));
    }
    
    // Get all role notifications
    @GetMapping("/role")
    public List<Notification> getRoleNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        return service.getByRole(user.getRole().getName());
    }

    // Create notification for one user
    @PostMapping("/create-notif/user/{username}")
    public Notification createForUser(@PathVariable String username, @RequestBody Notification notification) {
        return service.createNotification(notification.getTitle(), notification.getMessage(), username);
    }

    // Create notification for a role
    @PostMapping("/create-notif/role/{role}")
    public void createForRole(@PathVariable String role, @RequestBody Notification notification) {
        service.createNotificationForRole(notification.getTitle(), notification.getMessage(), role);
    }

    //Mark all as read
    @PatchMapping("/user/read-all")
    public ResponseEntity<?> markAllAsRead() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        service.markAllAsRead(username);
        
        return ResponseEntity.ok(Map.of("message", "All notifications marked as read", "user", username));
    }
    
 // Delete a notification
    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        service.deleteNotification(id);
    }
}
