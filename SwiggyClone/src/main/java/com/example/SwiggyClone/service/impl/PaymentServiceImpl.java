package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.PaymentDTO;
import com.example.SwiggyClone.dto.PaymentRequestDTO;
import com.example.SwiggyClone.entity.Order;
import com.example.SwiggyClone.entity.Payment;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.mapper.PaymentMapper;
import com.example.SwiggyClone.repository.OrderRepository;
import com.example.SwiggyClone.repository.PaymentRepository;
import com.example.SwiggyClone.service.inter.NotificationService;
import com.example.SwiggyClone.service.inter.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;
    private final PaymentMapper mapper;

    @Override
    @Transactional
    public PaymentDTO processPayment(PaymentRequestDTO requestDTO) {
        // 1. Fetch the Order from the database to get the trusted amount.
        Order order = orderRepository.findById(requestDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + requestDTO.getOrderId()));

        // Optional: Check if the order is already paid.
        if (!order.getStatus().equals("PLACED")) {
            throw new IllegalStateException("Order is not in a payable state. Current status: " + order.getStatus());
        }

        // 2. Create the Payment entity with the SERVER-SIDE amount.
        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getTotalAmount()) // ✅ CRITICAL: Using the trusted amount from the order.
                .paymentMethod(requestDTO.getPaymentMethod())
                .status("PENDING") // Initial status
                .build();

        // 3. Simulate payment processing.
        // In a real app, you would integrate with a payment gateway (Stripe, Razorpay) here.
        payment.setStatus("SUCCESS");
        Payment savedPayment = paymentRepository.save(payment);

        // 4. Update the order status upon successful payment.
        order.setStatus("PAID");
        orderRepository.save(order);

        // 5. Send a notification to the user.
        notificationService.sendOrderStatusUpdate(order.getId(), "PAID", order.getCustomer().getId());

        return mapper.toDto(savedPayment);
    }

    @Override
    public PaymentDTO getPaymentDetails(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
    }
}