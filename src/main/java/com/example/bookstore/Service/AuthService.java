package com.example.bookstore.Service;

import com.example.bookstore.Model.Role;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.Security.JwtUtil;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
private JavaMailSender mailSender;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        if (user.getRole() == Role.ADMIN && userRepository.existsByRole(Role.ADMIN)) {
            throw new RuntimeException("Admin already registered!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Registered Successfully!";
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
                System.out.println("Attempting login for: " + user.getEmail());
System.out.println("User from DB: " + user.getEmail() + ", Encoded password: " + user.getPassword());


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // return jwtUtil.generateToken(email);
       
return jwtUtil.generateToken(user); // ✅ now it works

}

public void forgotPassword(String email) {
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email cannot be empty");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        sendResetEmail(email, resetToken);
    }

    private void sendResetEmail(String email, String resetToken) {
        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
        //String resetUrl = "http://your-public-ip:8080/reset-password?token=" + resetToken;
       // String resetLink = "http://localhost:8000/reset-password?token=" + resetToken;

        System.out.println("🔹 Preparing to send reset password email to: " + email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Your Password");
        message.setText("Click the following link to reset your password: " + resetLink);
       //message.setText("Click the following link to reset your password: " + resetUrl);

        mailSender.send(message);
        System.out.println("✅ Email sent successfully to: " + email);
    }

    public String resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);

        return "Password has been reset successfully.";
    }
 

}
