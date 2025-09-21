package com.example.SwiggyClone.controller;

import com.example.SwiggyClone.dto.NotificationDTO;
import com.example.SwiggyClone.service.inter.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@PathVariable Long userId) {

        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }
}