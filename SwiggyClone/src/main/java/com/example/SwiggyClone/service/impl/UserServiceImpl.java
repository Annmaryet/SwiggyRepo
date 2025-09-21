package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.AuthRequest;
import com.example.SwiggyClone.dto.AuthResponse;
import com.example.SwiggyClone.dto.UserDTO;
import com.example.SwiggyClone.entity.Role;
import com.example.SwiggyClone.entity.User;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.exception.UserAlreadyExistsException;
import com.example.SwiggyClone.repository.RoleRepository;
import com.example.SwiggyClone.repository.UserRepository;
import com.example.SwiggyClone.service.inter.UserService;
import com.example.SwiggyClone.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Utility class to generate JWT tokens

    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists.");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Default role 'ROLE_USER' not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User newUser = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(encodedPassword)
                .roles(roles)
                .build();

        User savedUser = userRepository.save(newUser);
        log.info("Registered new user: {}", savedUser.getEmail());
        return toDto(savedUser);
    }

    @Override
    public UserDTO authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        return toDto(user);
    }

    @Override
    public AuthResponse authenticateUser(@Valid AuthRequest authRequest) {
        UserDTO userDTO = authenticate(authRequest.getEmail(), authRequest.getPassword());
        String token = jwtUtil.generateToken(userDTO.getEmail());
        log.info("User authenticated: {}", userDTO.getEmail());
        return new AuthResponse(token, "Authentication successful", userDTO);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equals(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email " + userDTO.getEmail() + " is already in use.");
        }

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        // Password update should be handled in a separate endpoint

        User updatedUser = userRepository.save(user);
        log.info("Updated user: {}", updatedUser.getEmail());
        return toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    // ================== PRIVATE MAPPINGS ==================
    private UserDTO toDto(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(roles) // Corrected from .role(roles)
                .build();
    }
}
