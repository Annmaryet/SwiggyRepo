package com.example.SwiggyClone.controller;

import com.example.SwiggyClone.dto.PaymentDTO;
import com.example.SwiggyClone.dto.PaymentRequestDTO;
import com.example.SwiggyClone.service.inter.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * CORRECTED: Accepts a secure DTO that doesn't contain the amount.
     */
    @PostMapping("/process")
    public ResponseEntity<PaymentDTO> processPayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        return ResponseEntity.ok(paymentService.processPayment(requestDTO));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentDetails(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId));
    }
}