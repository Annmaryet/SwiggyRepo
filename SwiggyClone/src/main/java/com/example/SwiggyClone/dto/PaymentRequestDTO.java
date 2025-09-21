package com.example.SwiggyClone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "Order ID is required to process payment")
    private Long orderId;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod; // e.g., "CARD", "UPI", "COD"
}