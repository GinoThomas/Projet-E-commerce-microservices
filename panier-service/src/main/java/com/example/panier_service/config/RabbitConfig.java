package com.example.panier_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "shop.exchange";
    public static final String CART_ROUTING_KEY = "cart.checkedout";
    public static final String CART_QUEUE = "payment.cart.queue"; // Queue du microservice Paiement

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue cartQueue() {
        return new Queue(CART_QUEUE);
    }

    @Bean
    public Binding cartBinding() {
        return BindingBuilder.bind(cartQueue())
                             .to(exchange())
                             .with(CART_ROUTING_KEY);
    }
}
