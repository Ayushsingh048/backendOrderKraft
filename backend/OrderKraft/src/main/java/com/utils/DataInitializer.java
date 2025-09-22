package com.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dto.UserDTO;
import com.service.UserService;

@Configuration
public class DataInitializer {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Bean
    CommandLineRunner initDefaultAdmin() {
        return args -> {
            try {
                // check if admin already exists (by email in your case)
                if (!userService.emailExists("admin@orderkraft.com")) {
                    UserDTO adminDto = new UserDTO();
                    adminDto.setUsername("admin");
                    adminDto.setEmail("admin@orderkraft.com");
                    adminDto.setPassword("admin123");   // ⚠️ Will be encoded in createUser()
                    adminDto.setRoleName("ADMIN");      // must exist in Role table
                    adminDto.setStatus("ACTIVE");       // adjust to match your entity
                    adminDto.setUserSession(null);      // or default value if required

                    userService.createUser(adminDto);
                    System.out.println("✅ Default admin user created!");
                } else {
                    System.out.println("ℹ️ Admin user already exists, skipping...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
