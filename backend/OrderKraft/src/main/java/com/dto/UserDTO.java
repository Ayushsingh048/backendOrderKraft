package com.dto;

public class UserDTO {
    private String username;
    private String email;
    private String status;
    private String userSession;
    private String roleName;

    public UserDTO() {}

    public UserDTO(String username, String email, String status, String session, String roleName) {
        this.username = username;
        this.email = email;
        this.status = status;
        this.userSession = userSession;
        this.roleName = roleName;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUserSession() { return userSession; }
    public void setUserSession(String userSession) { this.userSession = userSession; }


    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
