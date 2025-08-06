package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.OrderDTO;
import com.example.SwiggyClone.entity.Order;
import com.example.SwiggyClone.ResourceNotFoundException;
import com.example.SwiggyClone.repository.OrderRepository;
import com.example.SwiggyClone.service.inter.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = Order.builder()
                .customerName(orderDTO.getCustomerName())
                .orderDate(orderDTO.getOrderDate())
                .status(orderDTO.getStatus())
                .totalAmount(orderDTO.getTotalAmount())
                .build();

        Order savedOrder = orderRepository.save(order);

        return OrderDTO.builder()
                .id(savedOrder.getId())
                .customerName(savedOrder.getCustomerName())
                .orderDate(savedOrder.getOrderDate())
                .status(savedOrder.getStatus())
                .totalAmount(savedOrder.getTotalAmount())
                .build();
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(order -> OrderDTO.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .build()).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return OrderDTO.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
