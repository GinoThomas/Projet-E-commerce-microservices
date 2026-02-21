package com.example.suivi_stat_service.messaging;

import com.example.suivi_stat_service.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatsEventProducer {

    private final RabbitTemplate rabbitTemplate;

    // Injection de RabbitTemplate par Spring
    public StatsEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Méthode pour envoyer l'événement "TransactionProcessed"
    public void sendTransactionProcessed(Transaction transaction) {
        rabbitTemplate.convertAndSend(
            "shop.exchange",           // exchange RabbitMQ
            "transaction.processed",   // routing key
            transaction               // payload
        );
        System.out.println("TransactionProcessed publié : " + transaction.getId());
    }
}

