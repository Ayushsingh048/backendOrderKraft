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

    private String username;          // target user
    private String role;              // resolved from user’s role

    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification() {}

    public Notification(String title, String message, String role, String username) {
        this.title = title;
        this.message = message;
        this.role = role;
        this.username = username;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean getRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
