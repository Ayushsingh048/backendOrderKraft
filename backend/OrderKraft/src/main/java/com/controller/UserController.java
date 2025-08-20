package com.controller;

import com.dto.UserDTO;
import com.dto.PasswordResetRequest;
import com.dto.PasswordUpdateDTO;
import com.entity.User;
import com.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("users")
//@PreAuthorize("hasRole('ADMIN')") // Class-level: all endpoints secured for ADMIN by default!
public class UserController {

    @Autowired
    private UserService userService;
    
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
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

// update the user details - by admin 
@PutMapping("/update/admin/{id}")
public ResponseEntity<User> updateUserByAdmin(@PathVariable Long id, @RequestBody UserDTO dto) {
    return ResponseEntity.ok(userService.updateUserByAdmin(id, dto));
}


// update the username,email - by user

@PutMapping("/update/profile/{id}")
public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody UserDTO dto) {
    return ResponseEntity.ok(userService.updateUserProfile(id, dto));
}

// update password 
@PutMapping("/update/password/{id}")
public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO dto) {
    try {
        userService.updatePassword(id, dto);
        return ResponseEntity.ok("Password updated successfully.");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage()); // For incorrect password
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage()); // User not found
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
} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
}
}


}

