package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.entity.Notification;
import com.entity.Role;
import com.entity.User;
import com.repository.NotificationRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository repo;

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private RoleRepository roleRepo;

    // Create for single user
    public Notification createNotification(String title, String message, String username) {
    	
        Notification notif = new Notification();
        notif.setTitle(title);
        notif.setMessage(message);
        notif.setUser(this.getUser(username));
        System.out.println("Notification created...");
        Notification saved = repo.save(notif);

        // Push to user and role channels
//        messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/notifications", saved);
//        messagingTemplate.convertAndSend("/topic/" + user.getRole().getName(), saved);

        return saved;
    }

    // Create for a whole role
    public Notification createNotificationForRole(String title, String message, String roleName) {
    	
    	Optional<Role> role = roleRepo.findByName(roleName);
    	
    	Notification notif = new Notification();
        notif.setTitle(title);
        notif.setMessage(message);
        notif.setRole(role.get());
        System.out.println("Notification created...");
        return repo.save(notif);
    }

    public List<Notification> getByUsername(String username) {

        return repo.findByUser(this.getUser(username));
    }
    
    // Get last 10 notifications for user
    public List<Notification> getLast10ByUsername(String username) {

        return repo.findTop10ByUserOrderByCreatedAtDesc(this.getUser(username));
    }
    
    public List<Notification> getByRole(String roleName) {
    	Optional<Role> role = roleRepo.findByName(roleName);
    	
        return repo.findByRole(role.get());
    }
    
    public List<Notification> getUserAndRoleNotifications(String username) {
        // Fetch the user
        User user = this.getUser(username);

        // Personal notifications
        List<Notification> userNotifications =
                repo.findTop5ByUserOrderByCreatedAtDesc(user);

        // Role notifications
        List<Notification> roleNotifications =
                repo.findTop5ByRoleOrderByCreatedAtDesc(user.getRole());

        // Merge both lists
        List<Notification> allNotifications = new ArrayList<>();
        allNotifications.addAll(userNotifications);
        allNotifications.addAll(roleNotifications);

        allNotifications.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        return allNotifications;
    }

    
    public void markAllAsRead(String username) {
    	
        List<Notification> notifications = repo.findByUser(this.getUser(username));
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
    
    //Helper: Get User by username
    private User getUser(String username) {
    	return userRepo.findByUsername(username).get();
    }

}