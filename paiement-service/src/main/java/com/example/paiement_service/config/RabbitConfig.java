package com.example.paiement_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "shop.exchange";

    // Queue pour recevoir les événements du panier
    public static final String CART_QUEUE = "payment.cart.queue";
    public static final String CART_ROUTING_KEY = "cart.checkedout";

    // Queue pour publier les transactions créées
    public static final String TRANSACTION_QUEUE = "stat.transaction.queue";
    public static final String TRANSACTION_ROUTING_KEY = "transaction.created";

    @Bean
    public Queue cartQueue() {
        return new Queue(CART_QUEUE);
    }

    @Bean
    public Queue transactionQueue() {
        return new Queue(TRANSACTION_QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding cartBinding() {
        return BindingBuilder.bind(cartQueue())
                             .to(exchange())
                             .with(CART_ROUTING_KEY);
    }

    @Bean
    public Binding transactionBinding() {
        return BindingBuilder.bind(transactionQueue())
                             .to(exchange())
                             .with(TRANSACTION_ROUTING_KEY);
    }
}
