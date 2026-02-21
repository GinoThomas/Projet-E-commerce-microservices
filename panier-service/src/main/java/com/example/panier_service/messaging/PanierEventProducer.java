package com.example.panier_service.messaging;

import com.example.panier_service.model.Panier;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PanierEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public PanierEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPanierCheckedOut(Panier panier) {
        rabbitTemplate.convertAndSend(
            "shop.exchange",
            "cart.checkedout",
            panier
        );
        System.out.println("PanierCheckedOut envoyé : " + panier.getUserId());
    }
}

/* RabbitTemplate convertira automatiquement l'objet Panier en JSON pour RabbitMQ. */