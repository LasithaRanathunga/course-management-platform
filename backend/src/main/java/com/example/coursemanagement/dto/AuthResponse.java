package com.example.coursemanagement.dto;

import com.example.coursemanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private User.Role role;
}
