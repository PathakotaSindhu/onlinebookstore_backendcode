package com.example.bookstore.Security;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.Model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


private final JwtUtil jwtUtil;
private final UserRepository userRepository;

public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
}

@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    String token = authHeader.substring(7);
    String email = jwtUtil.extractEmail(token);

    System.out.println("Token: " + token);
    System.out.println("Email from token: " + email);
    //System.out.println("Extracted email from token: " + email);
    //System.out.println("Authenticated user: " + User.getFullName());
    
    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        User user = userRepository.findByEmail(email).orElse(null);

        // if (user != null) {
        //     // ✅ Prefix "ROLE_" to make it work with Spring Security
        //     String role = "ROLE_" + user.getRole().name();
        //     var authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        //     UsernamePasswordAuthenticationToken authToken =
        //             new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);
        //     authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //     SecurityContextHolder.getContext().setAuthentication(authToken);
        // }

        if (user != null) {
            var authorities = java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
            UsernamePasswordAuthenticationToken authToken =
                   // new UsernamePasswordAuthenticationToken(user, null, authorities);
                   new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        
    }

    filterChain.doFilter(request, response);
}


}

