package com.micro.client.messaging;

import com.micro.client.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientValidationProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendClientValidationResponse(ClientValidationResponse response) {
        log.info("Enviando respuesta de validación para cliente ID: {} [correlationId: {}]",
                response.getClientId(), response.getCorrelationId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CLIENT_EXCHANGE,
                RabbitMQConfig.CLIENT_VALIDATION_RESPONSE_ROUTING_KEY,
                response
        );

        log.info("Respuesta enviada correctamente");
    }
}
