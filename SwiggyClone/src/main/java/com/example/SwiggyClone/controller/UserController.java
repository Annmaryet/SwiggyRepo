package com.example.SwiggyClone.controller;

import com.example.SwiggyClone.dto.UserDTO;
import com.example.SwiggyClone.service.inter.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint to get a list of all users. (Typically admin-only)
     * @return A list of all users.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Endpoint to get a single user by their ID.
     * @param id The ID of the user.
     * @return The user's details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Endpoint to update a user's information.
     * @param id The ID of the user to update.
     * @param userDTO The updated user details.
     * @return The updated user's details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        // Note: Password in the userDTO will be ignored by the service.
        // A separate endpoint for password change is recommended.
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    /**
     * Endpoint to delete a user. (Typically admin-only)
     * @param id The ID of the user to delete.
     * @return A success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with ID " + id + " deleted successfully.");
    }
}