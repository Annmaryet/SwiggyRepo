package com.example.SwiggyClone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Message is required")
    private String message;

    // ADDED: Type to categorize the notification (e.g., "ORDER_UPDATE", "PROMOTION").
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}