package com.micro.client.messaging;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ClientValidationRequest implements Serializable {

    private Long clientId;
    private String correlationId;
}
