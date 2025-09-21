package com.example.SwiggyClone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {

    private String token;

    private String message;

    // CORRECTED: This field now correctly matches what your service provides.
    private UserDTO user;
}