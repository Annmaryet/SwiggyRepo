package com.example.SwiggyClone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private String customerName;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;

    // CORRECTED: An order contains OrderItemDTOs, not CartItemDTOs.
    private List<OrderItemDTO> items;
}