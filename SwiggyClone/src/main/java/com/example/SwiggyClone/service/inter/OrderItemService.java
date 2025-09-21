package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.OrderItemDTO;
import java.util.List;

public interface OrderItemService {

    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);

    List<OrderItemDTO> getAllOrderItems();

    // ADDED: A crucial method to get all items for a specific order.
    List<OrderItemDTO> getOrderItemsByOrderId(Long orderId);

    OrderItemDTO getOrderItemById(Long id);

    OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);

    void deleteOrderItem(Long id);
}