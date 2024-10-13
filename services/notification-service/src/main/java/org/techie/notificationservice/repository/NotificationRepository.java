package org.techie.notificationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.techie.notificationservice.entity.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
