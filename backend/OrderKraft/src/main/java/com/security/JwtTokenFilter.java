package com.security;

import com.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom filter that runs once per request to handle JWT authentication from cookies.
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;

    // JWT provider component for token operations like validation and extraction
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    JwtTokenFilter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Main filtering logic. This method runs for each incoming HTTP request.
     * It checks for a valid JWT token in cookies and sets the Spring Security context accordingly.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Try to retrieve the JWT token from cookies
        String token = getTokenFromCookies(request);
        System.out.println("Token in Filter: " + token);
        // If the token exists and is valid
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // Extract the username from the token
        	System.out.println("Token is valid");
            String username = jwtTokenProvider.getUsernameFromToken(token);
            System.out.println("Username from token: " + username);
            // Extract the role (e.g., USER or ADMIN)
            String role = jwtTokenProvider.extractRole(token);
            System.out.println("Role from token: " + role);
            // Convert role to Spring Security format (e.g., ROLE_ADMIN)
            String authority = "ROLE_" + role.toUpperCase().replace(" ", "_");
            System.out.println("Authority: " + authority);
            // Wrap the role in a GrantedAuthority list
            List<GrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority(authority));

            // Create an authentication object with the user details
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(username, null, authorities);
//            UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, authorities
            );
            
            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("Authentication set in context.");
        }

        // Continue the filter chain regardless of authentication
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the "jwt" cookie.
     * Returns null if the cookie is not present.
     */
    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
        	System.out.println("No cookies found in the request.");
        return null;
        }
        for (Cookie cookie : request.getCookies()) {
        	System.out.println("Found cookie: " + cookie.getName() + " = " + cookie.getValue());

            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue(); // Return the token value
            }
        }

        return null; // No jwt cookie found
    }
}
