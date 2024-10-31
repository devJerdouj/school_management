package com.example.notificationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.notificationservice.entity.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
