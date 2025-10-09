package com.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final RestTemplate restTemplate = new RestTemplate();

    // Main project context
    private static final String PROJECT_CONTEXT = """
        OrderKraft ERP System Project Context:

1. User Registration:
- Only Admin users can create new user accounts and assign roles.
- Self-registration is NOT allowed; normal users cannot register themselves.
- If a user is not logged in and tries to interact, inform them that they must contact an admin to create an account.

2. Login and Forgot Password:
- Login: Enter registered email and password on the login page, then click Login.
- Forgot Password: Click 'Forgot Password', enter your email, verify OTP, and set a new password.
- OTP contains only 6 digits (numbers only). If not received, check spam or resend.
- Wrong OTP shows an error; account lock requires admin activation.
- Session lasts 1 hour; if expired, log in again.

3. Inventory Management:
- Add new item: Open Inventory, click 'Add', enter product details and quantity, then save.
- View stock: Click 'View All' in Inventory.
- Low stock alerts appear in Alerts section or stock reports.
- Update stock after receiving items: Mark the related order as 'Received'.

4. Order Management:
- Create order: Go to Orders, click 'Create Order', fill order date, delivery date, order name, choose supplier, add items, then submit.
- Track orders: View Orders section for status.
- Mark order as received: Click 'Mark as Received' when goods arrive.
- Editing: Edit if not submitted, otherwise cancel and create a new order.
- Wrong supplier: Edit before submitting or cancel and re-create.

5. Supplier Management:
- Add supplier: Go to Suppliers, click 'Add Supplier', fill name, email, phone, address, account number, then save.
- View all suppliers: Click 'View All' in Suppliers section.
- Approve suppliers: Admin rights required; approve or reject buttons.

6. Role-based Access:
- Admin: Can view all reports and dashboards, manage users, and create new accounts.
- Procurement Officers & Sales Managers: Can create new orders.
- Inventory Managers: Can manage inventory.

7. Reports:
- Recent Orders: Reports/Dashboard → 'Recent Orders'.
- Stock Reports: Reports → 'Stock Level'.
- Order Movement: Reports → 'Movements'.

8. Common Issues:
- Password mismatch: Ensure both password fields match.
- Session expired: Log in again.
- Page access restricted: Access based on role; contact admin for extra access.
    """;

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");

        // Fetch logged-in user from Spring Security context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userState;
        String answerStyle = "Instructions: Answer concisely in 1-2 sentences. Only provide essential information.";

        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            String roles = userDetails.getAuthorities().toString(); // e.g., [ROLE_ADMIN]
            userState = "User is logged in as: " + username + " with roles: " + roles;
        } else {
            // Not logged in → enforce admin-only registration message
            userState = "User is NOT logged in. Inform them that registration is admin-only and self-registration is not allowed.";
        }

        // Build final prompt for Ollama
        String fullPrompt = answerStyle + "\n" + PROJECT_CONTEXT + "\n" + userState +
                "\nUser: " + userMessage + "\nAssistant:";

        String ollamaUrl = "http://localhost:11434/api/generate";
        Map<String, Object> requestMap = Map.of(
                "model", "llama3",
                "prompt", fullPrompt,
                "stream", false
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(ollamaUrl, requestMap, Map.class);

        String output = response.getBody().get("response").toString();
        return Map.of("reply", output);
    }
}
