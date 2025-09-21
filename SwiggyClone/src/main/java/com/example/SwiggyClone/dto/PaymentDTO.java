package com.example.SwiggyClone.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDTO {
    private Long paymentId;

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @Positive(message = "Amount must be greater than 0")
    private double amount;

    private String status; // SUCCESS / FAILED / PENDING
    private String paymentMethod; // CARD / UPI / COD
}
