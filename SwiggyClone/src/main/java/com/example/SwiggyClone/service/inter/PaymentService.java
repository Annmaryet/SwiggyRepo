package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.PaymentDTO;
import com.example.SwiggyClone.dto.PaymentRequestDTO;

public interface PaymentService {

    /**
     * CORRECTED: Process a payment using a secure request object.
     * @param requestDTO contains orderId and paymentMethod
     * @return processed PaymentDTO with updated status
     */
    PaymentDTO processPayment(PaymentRequestDTO requestDTO);

    /**
     * Fetch payment details by id.
     * @param paymentId id of the payment
     * @return PaymentDTO
     */
    PaymentDTO getPaymentDetails(Long paymentId);
}