package com.example.paiement_service.messaging;

import com.example.paiement_service.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public TransactionEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTransactionCreated(Transaction transaction) {
        rabbitTemplate.convertAndSend(
            "shop.exchange",
            "transaction.created",
            transaction
        );
        System.out.println("TransactionCreated publié : " + transaction.getId());
    }
}

