package com.micro.client.messaging;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ClientValidationResponse implements Serializable {

    private Long clientId;
    private Boolean exists;
    private Boolean active;
    private String clientName;
    private String clientEmail;
    private String correlationId;
}
