package com.example.SwiggyClone.controller;

import com.example.SwiggyClone.dto.AuthRequest;
import com.example.SwiggyClone.dto.AuthResponse;
import com.example.SwiggyClone.dto.UserDTO;
import com.example.SwiggyClone.service.inter.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(userService.authenticateUser(authRequest));
    }
}
