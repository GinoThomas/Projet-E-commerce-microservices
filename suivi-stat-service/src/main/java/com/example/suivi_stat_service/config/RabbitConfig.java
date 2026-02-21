package com.example.suivi_stat_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "shop.exchange";

    // Queue pour recevoir les transactions créées
    public static final String TRANSACTION_QUEUE = "stat.transaction.queue";
    public static final String TRANSACTION_ROUTING_KEY = "transaction.created";

    // Queue optionnelle pour republier les transactions après traitement
    public static final String PROCESSED_QUEUE = "processed.transaction.queue";
    public static final String PROCESSED_ROUTING_KEY = "transaction.processed";

    @Bean
    public Queue transactionQueue() {
        return new Queue(TRANSACTION_QUEUE);
    }

    @Bean
    public Queue processedQueue() {
        return new Queue(PROCESSED_QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding transactionBinding() {
        return BindingBuilder.bind(transactionQueue())
                             .to(exchange())
                             .with(TRANSACTION_ROUTING_KEY);
    }

    @Bean
    public Binding processedBinding() {
        return BindingBuilder.bind(processedQueue())
                             .to(exchange())
                             .with(PROCESSED_ROUTING_KEY);
    }
}

