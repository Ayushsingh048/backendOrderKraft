package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.entity.Notification;
import com.entity.User;
import com.repository.NotificationRepository;
import com.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Create for single user
    public Notification createNotification(String title, String message, String username) {
        User user = userRepo.findByUsername(username).orElseThrow();

        Notification notif = new Notification();
        notif.setTitle(title);
        notif.setMessage(message);
        notif.setUsername(user.getUsername());
        notif.setRole(user.getRole().getName());

        Notification saved = repo.save(notif);

        // Push to user and role channels
        messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/notifications", saved);
        messagingTemplate.convertAndSend("/topic/" + user.getRole().getName(), saved);

        return saved;
    }

    // Create for a whole role
    public void createNotificationForRole(String title, String message, String roleName) {
        List<User> users = userRepo.findByRoleName(roleName);
        for (User user : users) {
            createNotification(title, message, user.getUsername());
        }
    }

    public List<Notification> getByUsername(String username) {
    	System.out.println(username);
    	System.out.println(repo.findByUsername(username));
        return repo.findByUsername(username);
    }
    
    // Get last 10 notifications for user
    public List<Notification> getLast10ByUsername(String username) {
        return repo.findTop10ByUsernameOrderByCreatedAtDesc(username);
    }
    
    public List<Notification> getByRole(String role) {
        return repo.findByRole(role);
    }
    
    public void markAllAsRead(String username) {
        List<Notification> notifications = repo.findByUsername(username);
        for (Notification n : notifications) {
            if (!n.getRead()) {
                n.setRead(true);
            }
        }
        repo.saveAll(notifications);
    }
    
 // ✅ Delete a notification
    public void deleteNotification(Long id) {
        repo.deleteById(id);
    }

}