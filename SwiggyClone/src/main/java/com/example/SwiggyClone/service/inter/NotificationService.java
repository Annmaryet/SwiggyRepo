package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {

    // These methods create and save notifications for specific events.
    void sendOrderConfirmation(Long orderId, Long userId);
    void sendOrderStatusUpdate(Long orderId, String status, Long userId);
    void sendPromotionalNotification(String message, Long userId);

    // ADDED: A method to retrieve all notifications for a user.
    List<NotificationDTO> getNotificationsByUserId(Long userId);
}