package com.dto;

import com.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public CustomUserDetails(Optional<User> user2) {
		this.user = new User();
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming your User has a Role with a name
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();  // or email if you're using email to login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // or custom logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // or custom logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // or custom logic
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equalsIgnoreCase("active"); // or custom logic
    }
}
