package com.micro.client.service;

import com.micro.client.dto.ClientRequest;
import com.micro.client.dto.ClientResponse;
import com.micro.client.exception.ClientNotFoundException;
import com.micro.client.exception.DuplicateEmailException;
import com.micro.client.mapper.ClientMapper;
import com.micro.client.model.Client;
import com.micro.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    @Transactional
    public ClientResponse createClient(ClientRequest request) {
        log.info("Creando cliente con email: {}", request.getEmail());

        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        Client client = clientMapper.toEntity(request);
        Client savedClient = clientRepository.save(client);

        log.info("Cliente creado con ID: {}", savedClient.getId());

        return clientMapper.toResponse(savedClient);
    }

    public List<ClientResponse> getAllClients() {
        log.info("Obteniendo todos los clientes");

        List<Client> clients = clientRepository.findAll();
        return clientMapper.toResponseList(clients);
    }

    public ClientResponse getClientById(Long id) {
        log.info("Obteniendo cliente ID: {}", id);

        Client cliente = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return clientMapper.toResponse(cliente);
    }

    public ClientResponse updateClient(Long id, ClientRequest request) {
        log.info("Actualizando cliente ID: {}", id);

        Client cliente = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (!cliente.getEmail().equals(request.getEmail())
                && clientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        clientMapper.updateEntityFromRequest(request, cliente);
        Client updatedCliente = clientRepository.save(cliente);

        log.info("Cliente actualizado ID: {}", id);

        return clientMapper.toResponse(updatedCliente);
    }

    public ClientResponse partialUpdateClient(Long id, ClientRequest request) {
        log.info("Actualizando parcialmente cliente ID: {}", id);

        Client cliente = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (request.getEmail() != null
                && !cliente.getEmail().equals(request.getEmail())
                && clientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        clientMapper.updateEntityFromRequest(request, cliente);
        Client updatedCliente = clientRepository.save(cliente);

        log.info("Cliente actualizado parcialmente ID: {}", id);

        return clientMapper.toResponse(updatedCliente);
    }

    public void deleteClient(Long id) {
        log.info("Eliminando cliente ID: {}", id);

        Client cliente = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        clientRepository.delete(cliente);

        log.info("Cliente eliminado ID: {}", id);
    }
}