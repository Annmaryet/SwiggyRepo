package com.example.SwiggyClone.repository;

import com.example.SwiggyClone.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // ADDED: A method to find all notifications for a user, ordered by timestamp descending.
    List<Notification> findByUserIdOrderByTimestampDesc(Long userId);
}