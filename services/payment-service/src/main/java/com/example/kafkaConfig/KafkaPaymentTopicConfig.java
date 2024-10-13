package com.example.kafkaConfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaPaymentTopicConfig {

    @Bean
    public NewTopic paymentCompletedTopic()  {
        return TopicBuilder
                .name("payment_completed")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentOverdueTopic()  {
        return TopicBuilder
                .name("payment_overdue")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentUpcomingReminderTopic()  {
        return TopicBuilder
                .name("upcoming_payment_reminder")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
