package com.example.SwiggyClone.repository;

import com.example.SwiggyClone.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // CORRECTED: Change findByUser(User user) to findByUserId(Long userId)
    // This allows the service to query directly with the ID without fetching the User entity first.
    Optional<Cart> findByUserId(Long userId);
}