package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.UserDTO;
import com.example.SwiggyClone.entity.Role;
import com.example.SwiggyClone.entity.User;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.exception.UserAlreadyExistsException;
import com.example.SwiggyClone.repository.RoleRepository;
import com.example.SwiggyClone.repository.UserRepository;
import com.example.SwiggyClone.service.inter.UserService;
import lombok.RequiredArgsConstructor;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; // ✅ For secure password hashing

    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists.");
        }

        // Hash the password before saving
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        // Find and assign the default user role
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Default role 'ROLE_USER' not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User newUser = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(encodedPassword) // Save the hashed password
                .roles(roles)
                .build();

        User savedUser = userRepository.save(newUser);
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

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        // Do not update password here unless it's a specific password change endpoint

        User updatedUser = userRepository.save(user);
        return toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // ================== PRIVATE MAPPING ==================
    private UserDTO toDto(User user) {
        String role = user.getRoles().stream()
                .findFirst()
                .map(Role::getName)
                .orElse(null);

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(role)
                .build();
    }
}