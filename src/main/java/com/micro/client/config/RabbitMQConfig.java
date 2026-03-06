package com.micro.client.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CLIENT_EXCHANGE = "client_exchange";
    public static final String CLIENT_VALIDATION_QUEUE = "client_validation_queue";
    public static final String CLIENT_VALIDATION_RESPOOL_QUEUE = "client_validation_respo_queue";
    public static final String CLIENT_VALIDATION_ROUTING_KEY = "client_validation_routing_key";
    public static final String CLIENT_VALIDATION_RESPONSE_ROUTING_KEY = "client_validation_response_routing_key";

    @Bean
    public TopicExchange clientExchange() {
        return new TopicExchange(CLIENT_EXCHANGE);
    }

    @Bean
    public Queue clientValidationQueue() {return new Queue(CLIENT_VALIDATION_QUEUE, true);}

    @Bean
    public Queue clientValidationResponseQueue() {return new Queue(CLIENT_VALIDATION_RESPOOL_QUEUE, true);}

    @Bean
    public Binding clientValidationBinding() {
        return BindingBuilder
                .bind(clientValidationQueue())
                .to(clientExchange())
                .with(CLIENT_VALIDATION_ROUTING_KEY);
    }

    @Bean
    public Binding clientValidationResponseBinding() {
        return BindingBuilder
                .bind(clientValidationResponseQueue())
                .to(clientExchange())
                .with(CLIENT_VALIDATION_RESPONSE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
