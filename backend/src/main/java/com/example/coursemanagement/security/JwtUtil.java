package com.example.coursemanagement.security;

import com.example.coursemanagement.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Component
public class JwtUtil {

//    private final String SECRET = "Kb5cQh7vV0wR9gT4xP6nZm8qD3yJ2kL1aB7hU2vE9rM3pN4dS6yX8cW1zA0lF5oM"; // TODO: move to config
//    private final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final String SECRET = "fb1b56ab6cf517ad027e3a6eab31771f7821afe8e76a7a46fb3d7ab7e774a2f4d05ea2cd21f481f82153852858f715d0620672f058f8f22b32c9ae323cb5c02e";


    private final long EXPIRATION_TIME = 86400000; // 1 day

//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS512, SECRET)
//                .compact();
//    }


    public String generateToken(Long id, String username, String email, User.Role role) {
        return Jwts.builder()
                .setSubject(username) // still set a subject if needed
                .claim("id", id)
                .claim("email", email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public Long extractUserId(String token) {
        return ((Number) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .get("id")).longValue();
    }

    public String extractEmail(String token) {
        return (String) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .get("email");
    }

    public String extractRole(String token) {
        return (String) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }


    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


}
