package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.AuthRequest;
import com.example.SwiggyClone.dto.AuthResponse;
import com.example.SwiggyClone.dto.UserDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO authenticate(String email, String password);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);

    // ADDED: This new method must be in the interface.
    AuthResponse authenticateUser(@Valid AuthRequest authRequest);
}