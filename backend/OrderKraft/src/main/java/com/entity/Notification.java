package com.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
	    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 1)
    private Long id;

    private String title;
    private String message;

    private Boolean read = false;  // UNREAD, READ

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;          // target user
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;              // resolved from user’s role

    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification() {}

    public Notification(String title, String message, Role role, User user) {
        this.title = title;
        this.message = message;
        this.role = role;
        this.user = user;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Boolean getRead() { return read; }
    public void setRead(Boolean read) { this.read = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
    
    
}
