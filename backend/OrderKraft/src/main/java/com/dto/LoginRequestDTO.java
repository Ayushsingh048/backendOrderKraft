package com.dto;

public class LoginRequestDTO {

    private String email;

    private String password;

    // Getters and setters
    
    
    
    public String getEmail() {
        return email;
    }

    public LoginRequestDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}