package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.entity.Notification;
import com.entity.User;
import com.repository.NotificationRepository;
import com.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository repo;

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private UserRepository userRepo;

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
	@Override
	public void createNotificationForRole(String title, String message, String roleName) {
		
    	for(User user: userRepo.findByRoleName(roleName)) {
    	this.createNotification(title, message, user.getUsername());
    	}
        System.out.println("Notification created...");
	}
	
    public List<Notification> getByUsername(String username) {

        return repo.findByUser(this.getUser(username));
    }
    
    // Get last 10 notifications for user
    public List<Notification> getLast10ByUsername(String username) {

        return repo.findTop10ByUserOrderByCreatedAtDesc(this.getUser(username));
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