package com.Fawry.Team_Management_service.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String TEAM_EXCHANGE = "teamExchange";
    public static final String TEAM_QUEUE = "teamQueue";
    public static final String TEAM_ROUTING_KEY = "team.*";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TEAM_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(TEAM_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TEAM_ROUTING_KEY);
    }
}