// package com.example.bookstore.Security;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;

// import java.security.Key;
// import java.util.Date;

// @Component
// public class JwtUtil {

//     private static final String SECRET = "7iiz1jmonnajwilgfe3tnawsxwkt9xyu"; // should be at least 256 bits (32 chars)
//     private static final long EXPIRATION_TIME = 86400000; // 1 day

//     private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

//     public String generateToken(String email) {
//         return Jwts.builder()
//                 .setSubject(email)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public String extractEmail(String token) {
//         return getClaims(token).getSubject();
//     }

//     public boolean isTokenValid(String token) {
//         return !getClaims(token).getExpiration().before(new Date());
//     }

//     private Claims getClaims(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//     }
// }

// package com.example.bookstore.Security;

// import com.example.bookstore.Model.User;
// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;

// import java.security.Key;
// import java.util.Date;

// @Component
// public class JwtUtil {

//     private static final String SECRET = "7iiz1jmonnajwilgfe3tnawsxwkt9xyu"; // must be 32+ chars
//     private static final long EXPIRATION_TIME = 86400000; // 1 day

//     private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

//     // ✅ Generate token with email and role
//     public String generateToken(User user) {
//         return Jwts.builder()
//                 .setSubject(user.getEmail())
//                 .claim("role", user.getRole().name())
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     // ✅ Extract email from token
//     public String extractEmail(String token) {
//         return getClaims(token).getSubject();
//     }

//     // ✅ Validate expiration
//     public boolean isTokenValid(String token) {
//         return !getClaims(token).getExpiration().before(new Date());
//     }

//     private Claims getClaims(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//     }
// }


package com.example.bookstore.Security;

import com.example.bookstore.Model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "7iiz1jmonnajwilgfe3tnawsxwkt9xyu"; // must be 32+ chars
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ Generate token with email and role
    // public String generateToken(User user) {
    //     return Jwts.builder()
    //             .setSubject(user.getEmail())
    //             .claim("role", user.getRole().name()) // 👈 Include role claim
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    //             .signWith(key, SignatureAlgorithm.HS256)
    //             .compact();
    // }
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", "ROLE_" + user.getRole().name()) // Add "ROLE_" prefix
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    

    // ✅ Extract email
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Extract role
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // ✅ Validate token
    public boolean isTokenValid(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
