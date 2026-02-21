package com.example.suivi_stat_service.messaging;

import com.example.suivi_stat_service.model.Transaction;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    private final StatsEventProducer producer; // si on republie

    public TransactionConsumer(StatsEventProducer producer) {
        this.producer = producer;
    }

    @RabbitListener(queues = "stat.transaction.queue")
    public void handleTransactionCreated(Transaction transaction) {
        System.out.println("TransactionCreated reçu : " + transaction.getId());

        // Ici tu peux faire le traitement statistique
        // Ex: incrémenter le total, sauvegarder dans la base, etc.

        // Optionnel : republier après traitement
        producer.sendTransactionProcessed(transaction);
    }
}

