package com.example.SwiggyClone.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDTO {
    private Long foodItemId;
    private String foodName;

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;

    private double price;
}