package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.NotificationDTO;
import com.example.SwiggyClone.entity.Notification;
import com.example.SwiggyClone.entity.User;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.repository.NotificationRepository;
import com.example.SwiggyClone.repository.UserRepository;
import com.example.SwiggyClone.service.inter.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // Helper method to create and save a notification
    private void createAndSend(Long userId, String message, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .type(type)
                .timestamp(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
        // In a real app, this is where you would also push to a mobile/web client.
    }

    @Override
    public void sendOrderConfirmation(Long orderId, Long userId) {
        String message = "✅ Your order #" + orderId + " has been confirmed and is being prepared!";
        createAndSend(userId, message, "ORDER_UPDATE");
    }

    @Override
    public void sendOrderStatusUpdate(Long orderId, String status, Long userId) {
        String message = "ℹ️ Update on order #" + orderId + ": Your order is now " + status + ".";
        createAndSend(userId, message, "ORDER_UPDATE");
    }

    @Override
    public void sendPromotionalNotification(String message, Long userId) {
        createAndSend(userId, message, "PROMOTION");
    }

    @Override
    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByTimestampDesc(userId);
        return notifications.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ================== PRIVATE MAPPING ==================
    private NotificationDTO toDto(Notification notification) {
        return NotificationDTO.builder()
                .message(notification.getMessage())
                .type(notification.getType())
                .timestamp(notification.getTimestamp())
                .build();
    }
}