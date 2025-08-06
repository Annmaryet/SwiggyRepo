package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.OrderItemDTO;
import com.example.SwiggyClone.entity.OrderItem;
import com.example.SwiggyClone.ResourceNotFoundException;
import com.example.SwiggyClone.repository.OrderItemRepository;
import com.example.SwiggyClone.service.inter.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = OrderItem.builder()
                .orderId(orderItemDTO.getOrderId())
                .foodItemId(orderItemDTO.getFoodItemId())
                .quantity(orderItemDTO.getQuantity())
                .price(orderItemDTO.getPrice())
                .build();

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        return convertToDTO(savedOrderItem);
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found with id: " + id));

        return convertToDTO(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order Item not found with id: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found with id: " + id));

        // ✅ Updating fields
        existingOrderItem.setOrderId(orderItemDTO.getOrderId());
        existingOrderItem.setFoodItemId(orderItemDTO.getFoodItemId());
        existingOrderItem.setQuantity(orderItemDTO.getQuantity());
        existingOrderItem.setPrice(orderItemDTO.getPrice());

        OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);

        return convertToDTO(updatedOrderItem);
    }

    // ---------------- Helper Method ----------------
    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrderId())
                .foodItemId(orderItem.getFoodItemId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}
