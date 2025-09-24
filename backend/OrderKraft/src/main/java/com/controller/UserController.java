package com.controller;

import com.dto.UserDTO;
import com.dto.PasswordResetRequest;
import com.dto.PasswordUpdateDTO;
import com.entity.User;
import com.service.UserService;
//import org.springframework.core.io.UrlResource;
//import jakarta.annotation.Resource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("users")
//@PreAuthorize("hasRole('ADMIN')") // Class-level: all endpoints secured for ADMIN by default!
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
   // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    } 

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    // Method-level: override to allow both ADMIN and PRODUCTION_MANAGER
  //  @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTION_MANAGER')")

    @GetMapping("/search/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<User>> getUsersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(userService.getUsersByStatus(status));
    }

    @GetMapping("/search/session/{userSession}")
    public ResponseEntity<List<User>> getUsersBySession(@PathVariable String userSession) {
        return ResponseEntity.ok(userService.getUsersBySession(userSession));
    }
    
    @GetMapping("/search/email/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email){
    	return ResponseEntity.ok(userService.getUserByEmail(email));
    }
 
    @GetMapping("/search/accountnumber/{accountNumber}")
    public ResponseEntity<User> getUserByAccountNumber(@PathVariable String accountNumber) {
        return userService.getUserByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.usernameExists(username);
        return ResponseEntity.ok(exists);
    }


// update the user details - by admin 
@PutMapping("/update/admin/{id}")
public ResponseEntity<User> updateUserByAdmin(@PathVariable Long id, @RequestBody UserDTO dto) {
    return ResponseEntity.ok(userService.updateUserByAdmin(id, dto));
}


// update the username,email - by user

@PutMapping("/update/profile/{id}")
public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody UserDTO dto,HttpServletResponse response) {
    //return ResponseEntity.ok(userService.updateUserProfile(id, dto));
	User updatedUser = userService.updateUserProfile(id, dto);

    // 🔴 Invalidate JWT cookie here
    Cookie cookie = new Cookie("jwt", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(false); // true in prod
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);

    return ResponseEntity.ok(updatedUser);
	
}

// update password 
@PutMapping("/update/password/{id}")
public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO dto,HttpServletResponse response) {
    try {
        User updatedUser = userService.updatePassword(id, dto);
        // 🔴 Invalidate JWT cookie here
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true in prod
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(Map.of("message", "Password updated successfully."));
    } catch (IllegalArgumentException e) {
//        return ResponseEntity.status(400).body(e.getMessage());
        return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));// For incorrect password
    } catch (RuntimeException e) {
        //return ResponseEntity.status(404).body(e.getMessage()); // User not found
    	 return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
    }
}
//reset-password
//@PostMapping("/users/reset-password")
//public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request, Principal principal) {
//try {
//    userService.resetPasswordOnFirstLogin(request, principal.getName());
//    return ResponseEntity.ok("Password updated successfully.");
//} catch (IllegalArgumentException e) {
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//}
//}

//@PutMapping("/reset-password")
//public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request, Principal principal) {
//try {
//    userService.resetPasswordOnFirstLogin(request, principal.getName());
//    return ResponseEntity.ok("Password updated successfully.");
//} catch (IllegalArgumentException e) {
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//}
//}


@PutMapping("/reset-password")
public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request, Principal principal) {
try {
    userService.resetPasswordOnFirstLogin(request, principal.getName());
    return ResponseEntity.ok("Password updated successfully.");
} catch (IllegalArgumentException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
} catch (Exception e) {System.out.println(e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
}
}


private static final String UPLOAD_DIR = "uploads/profile-photos/";

@PostMapping("/{id}/upload-photo")
public ResponseEntity<String> uploadPhoto(@PathVariable Long id,
                                          @RequestParam("file") MultipartFile file) {
    try {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure directory exists
        File  dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        // Unique filename
        String fileName = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(filePath, file.getBytes());

        // Save file path in DB
        user.setProfilePhotoPath(filePath.toString());
        userRepository.save(user);

        return ResponseEntity.ok("Photo uploaded successfully: " + fileName);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error uploading photo: " + e.getMessage());
    }
}

@GetMapping("/{id}/photo")
public ResponseEntity<Resource> getPhoto(@PathVariable Long id) throws IOException {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (user.getProfilePhotoPath() == null) {
        return ResponseEntity.notFound().build();
    }

    Path path = Paths.get(user.getProfilePhotoPath());
    Resource resource = new UrlResource(path.toUri());

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource);
}


}

