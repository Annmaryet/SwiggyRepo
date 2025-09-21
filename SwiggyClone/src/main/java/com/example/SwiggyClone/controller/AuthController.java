package com.example.SwiggyClone.controller;

import com.example.SwiggyClone.dto.AuthRequest;
import com.example.SwiggyClone.dto.AuthResponse;
import com.example.SwiggyClone.dto.UserDTO;
import com.example.SwiggyClone.service.inter.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Endpoint to register a new user.
     * @param userDTO The user details for registration.
     * @return The created user's details with a 201 CREATED status.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    /**
     * Endpoint to authenticate a user and receive a JWT.
     * @param authRequest The login credentials (email and password).
     * @return An authentication response containing the JWT and user details.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = userService.authenticateUser(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}