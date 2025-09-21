package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.CartDTO;
import com.example.SwiggyClone.dto.OrderDTO;
import com.example.SwiggyClone.dto.OrderItemDTO;
import com.example.SwiggyClone.entity.*;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.repository.FoodItemRepository;
import com.example.SwiggyClone.repository.OrderRepository;
import com.example.SwiggyClone.repository.UserRepository;
import com.example.SwiggyClone.service.inter.CartService;
import com.example.SwiggyClone.service.inter.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;
    private final CartService cartService;

    @Override
    @Transactional
    public OrderDTO createOrder(Long userId) {
        CartDTO cart = cartService.getCartByUserId(userId);
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create an order from an empty cart.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Order order = Order.builder()
                .customer(user) // CORRECTED: Aligned with entity field name 'customer'
                .orderDate(LocalDateTime.now())
                .status("PLACED")
                .totalAmount(cart.getTotalPrice())
                .build();

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            FoodItem foodItem = foodItemRepository.findById(cartItem.getFoodItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("FoodItem not found with id: " + cartItem.getFoodItemId()));

            return OrderItem.builder()
                    .order(order)
                    .foodItem(foodItem)
                    .quantity(cartItem.getQuantity())
                    .price(foodItem.getPrice())
                    .build();
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems); // CORRECTED: Aligned with entity field name 'orderItems'
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);
        return toDto(savedOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        // CORRECTED: Using the correct repository method name
        return orderRepository.findByCustomerId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return toDto(order);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return toDto(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    // ================== HELPER METHOD ==================
    private OrderDTO toDto(Order order) {
        List<OrderItemDTO> itemDTOs = order.getOrderItems().stream() // CORRECTED: Aligned with entity field name
                .map(item -> OrderItemDTO.builder()
                        .id(item.getId())
                        .orderId(order.getId())
                        .foodItemId(item.getFoodItem().getId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getCustomer().getId()) // CORRECTED: Aligned with entity field name
                .customerName(order.getCustomer().getName()) // Assuming User has getName()
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(itemDTOs)
                .build();
    }
}