package com.example.SwiggyClone.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDTO {
    // This field is removed from the DTO to simplify the response. The primary identifier is the userId.
    // private Long id;

    private Long userId;
    private List<CartItemDTO> items;
    private double totalPrice; // This is a great addition! The service will now calculate this.
}