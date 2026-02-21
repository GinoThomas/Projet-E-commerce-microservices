package com.example.paiement_service.messaging;

import com.example.paiement_service.model.Transaction;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CartCheckedOutConsumer {

    private final TransactionEventProducer producer;

    public CartCheckedOutConsumer(TransactionEventProducer producer) {
        this.producer = producer;
    }

    @RabbitListener(queues = "payment.cart.queue")
    public void handleCartCheckedOut(String cartMessage) {
        System.out.println("CartCheckedOut reçu : " + cartMessage);

        // Ici tu crées la transaction en fonction du panier
        Transaction transaction = new Transaction();
        transaction.setId(System.currentTimeMillis());
        transaction.setMontant(100.0); // exemple
        transaction.setStatut("SUCCESS");

        // Publier l'événement TransactionCreated
        producer.sendTransactionCreated(transaction);
    }
}

