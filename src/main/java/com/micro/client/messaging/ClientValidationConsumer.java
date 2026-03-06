package com.micro.client.messaging;

import com.micro.client.config.RabbitMQConfig;
import com.micro.client.model.Client;
import com.micro.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientValidationConsumer {

    private final ClientRepository clientRepository;
    private final ClientValidationProducer producer;

    @RabbitListener(queues = RabbitMQConfig.CLIENT_VALIDATION_QUEUE)
    public void handleClientValidationRequest(ClientValidationRequest request) {
        log.info("Solicitud recibida para validar cliente ID: {} [correlationId: {}]",
                request.getClientId(), request.getCorrelationId());

        Optional<Client> clientOpt = clientRepository.findById(request.getClientId());

        ClientValidationResponse response;

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();

            response = ClientValidationResponse.builder()
                    .clientId(request.getClientId())
                    .exists(true)
                    .active(client.isActive())
                    .clientName(client.getName())
                    .clientEmail(client.getEmail())
                    .correlationId(request.getCorrelationId())
                    .build();

            log.info("Cliente encontrado: {} ({}), Activo: {}",
                    client.getName(), client.getEmail(), client.isActive());
        } else {
            response = ClientValidationResponse.builder()
                    .clientId(request.getClientId())
                    .exists(false)
                    .active(false)
                    .correlationId(request.getCorrelationId())
                    .build();

            log.warn("Cliente con ID {} no encontrado", request.getClientId());
        }

        producer.sendClientValidationResponse(response);
    }
}
