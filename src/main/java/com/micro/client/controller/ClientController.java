package com.micro.client.controller;

import com.micro.client.dto.ClientRequest;
import com.micro.client.dto.ClientResponse;
import com.micro.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        log.info("POST /clientes - Crear cliente: {}", request.getEmail());

        ClientResponse response = clientService.createClient(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        log.info("GET /clientes - Obtener todos los clientes");

        List<ClientResponse> clientes = clientService.getAllClients();

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) {
        log.info("GET /clientes/{} - Obtener cliente por ID", id);

        ClientResponse cliente = clientService.getClientById(id);

        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest request) {

        log.info("PUT /clientes/{} - Actualizar cliente", id);

        ClientResponse response = clientService.updateClient(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> partialUpdateClient(
            @PathVariable Long id,
            @RequestBody ClientRequest request) {

        log.info("PATCH /clientes/{} - Actualización parcial", id);

        ClientResponse response = clientService.partialUpdateClient(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.info("DELETE /clientes/{} - Eliminar cliente", id);

        clientService.deleteClient(id);

        return ResponseEntity.noContent().build();
    }
}