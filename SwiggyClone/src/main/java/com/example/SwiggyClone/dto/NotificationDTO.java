package com.example.SwiggyClone.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private String message;
    private String type; // ORDER_UPDATE / PROMOTION / SYSTEM
    private LocalDateTime timestamp;
}
