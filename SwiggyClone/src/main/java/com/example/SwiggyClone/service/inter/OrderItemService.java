package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.OrderItemDTO;
import java.util.List;

public interface OrderItemService {

    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);

    List<OrderItemDTO> getAllOrderItems();

    OrderItemDTO getOrderItemById(Long id);

    OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);

    void deleteOrderItem(Long id);
}
