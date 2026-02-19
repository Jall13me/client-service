package com.micro.client.exception;

public class ClientNotFoundException extends  RuntimeException {

    public ClientNotFoundException(Long id) {
        super("Cliente con " + id + " no encontrado");
    }
    public ClientNotFoundException(String message) {
        super(message);
    }
}
