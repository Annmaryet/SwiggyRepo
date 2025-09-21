package com.example.SwiggyClone.repository;

import com.example.SwiggyClone.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // CORRECTED: The field in the Order entity is "customer", so the method name must match.
    List<Order> findByCustomerId(Long customerId);
}