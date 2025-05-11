

package com.example.bookstore.Controller;

import com.example.bookstore.Model.User;
import com.example.bookstore.Service.AuthService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.login(user.getEmail(), user.getPassword());
    }
    //forgetpassword

//     @PostMapping("/forgot-password")
// public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
//     System.out.println("📥 Received JSON: " + request);
//     if (request == null || !request.containsKey("email")) {
//         return ResponseEntity.badRequest().body("❌ Email is required in request body");
//     }

//     String email = request.get("email");
//     authService.forgotPassword(email);
//     return ResponseEntity.ok("✅ Password reset link sent successfully to " + email);
// }

@PostMapping("/forgot-password")
public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody Map<String, String> request) {
    System.out.println("📥 Received JSON: " + request);
    
    if (request == null || !request.containsKey("email")) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", "❌ Email is required in request body");
        return ResponseEntity.badRequest().body(error);
    }

    String email = request.get("email");
    authService.forgotPassword(email);

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "✅ Password reset link sent successfully to " + email);

    return ResponseEntity.ok(response);
}
    //authService.forgotPassword(email);

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        String response = authService.resetPassword(token, newPassword);
        return ResponseEntity.ok(response);
    }
}
