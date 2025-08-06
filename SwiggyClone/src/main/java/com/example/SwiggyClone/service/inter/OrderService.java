package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long id);
    void deleteOrder(Long id);
}
