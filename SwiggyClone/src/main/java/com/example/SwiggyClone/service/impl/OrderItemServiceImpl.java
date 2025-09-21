package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.OrderItemDTO;
import com.example.SwiggyClone.entity.Order;
import com.example.SwiggyClone.entity.FoodItem;
import com.example.SwiggyClone.entity.OrderItem;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.repository.FoodItemRepository;
import com.example.SwiggyClone.repository.OrderRepository;
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
    // ADDED: Dependencies needed to fetch related entities.
    private final OrderRepository orderRepository;
    private final FoodItemRepository foodItemRepository;

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        // REFACTORED: Fetch the actual entities instead of just using IDs.
        Order order = orderRepository.findById(orderItemDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderItemDTO.getOrderId()));

        FoodItem foodItem = foodItemRepository.findById(orderItemDTO.getFoodItemId())
                .orElseThrow(() -> new ResourceNotFoundException("FoodItem not found with id: " + orderItemDTO.getFoodItemId()));

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .foodItem(foodItem)
                .quantity(orderItemDTO.getQuantity())
                .price(orderItemDTO.getPrice()) // Storing price ensures historical accuracy
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
    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId).stream()
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
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found with id: " + id));

        // Note: You would typically only update quantity. Changing the food item or order
        // is unusual but the logic is here if needed.
        FoodItem foodItem = foodItemRepository.findById(orderItemDTO.getFoodItemId())
                .orElseThrow(() -> new ResourceNotFoundException("FoodItem not found with id: " + orderItemDTO.getFoodItemId()));

        existingOrderItem.setFoodItem(foodItem);
        existingOrderItem.setQuantity(orderItemDTO.getQuantity());
        existingOrderItem.setPrice(orderItemDTO.getPrice());

        OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);
        return convertToDTO(updatedOrderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order Item not found with id: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    // ---------------- Helper Method ----------------
    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        // UPDATED: Gets IDs from the nested entity objects.
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .foodItemId(orderItem.getFoodItem().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}