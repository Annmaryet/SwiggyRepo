package com.example.SwiggyClone.controller;

import com.example.SwiggyClone.dto.OrderDTO;
import com.example.SwiggyClone.service.inter.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * CORRECTED: Create an order for a user based on their cart.
     * The user ID is taken from the path, not a request body, for security.
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long userId) {
        OrderDTO createdOrder = orderService.createOrder(userId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * ADDED: Get all orders for a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    /**
     * This endpoint is for an admin to see all orders in the system.
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    /**
     * ADDED: Update the status of an order (e.g., "PREPARING", "OUT_FOR_DELIVERY").
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Or throw a custom exception
        }
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, newStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order with ID " + id + " deleted successfully.");
    }
}