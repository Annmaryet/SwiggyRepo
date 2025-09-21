package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.OrderDTO;
import java.util.List;

public interface OrderService {

    // REFACTORED: Create an order for a specific user from their cart.
    OrderDTO createOrder(Long userId);

    List<OrderDTO> getAllOrders();

    // ADDED: Get all orders for a specific user.
    List<OrderDTO> getOrdersByUserId(Long userId);

    OrderDTO getOrderById(Long id);

    // ADDED: Update the status of an existing order.
    OrderDTO updateOrderStatus(Long orderId, String status);

    void deleteOrder(Long id);
}