package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.entity.Notification;
import com.entity.User;
import com.repository.UserRepository;
import com.service.CustomUserDetailsService;
import com.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @Autowired
    private UserRepository userRepo;

    // Get all user notifications
    @GetMapping("/user")
    public List<Notification> getUserNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return service.getByUsername(username);
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
    @PostMapping("/user/{username}")
    public Notification createForUser(@PathVariable String username, @RequestBody Notification notification) {
        return service.createNotification(notification.getTitle(), notification.getMessage(), username);
    }

    // Create notification for a role
    @PostMapping("/role/{role}")
    public void createForRole(@PathVariable String role, @RequestBody Notification notification) {
        service.createNotificationForRole(notification.getTitle(), notification.getMessage(), role);
    }

    // Mark as read
    @PatchMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {
        return service.markAsRead(id);
    }
}
