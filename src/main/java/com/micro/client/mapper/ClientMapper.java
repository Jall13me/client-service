package com.micro.client.mapper;

import com.micro.client.dto.ClientResponse;
import com.micro.client.dto.ClientRequest;
import com.micro.client.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClientMapper {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt",ignore = true)
        @Mapping(target = "active",ignore = true)
        Client toEntity(ClientRequest request);

        ClientResponse toResponse(Client client);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt",ignore = true)
        @Mapping(target = "active",ignore = true)
        void updateEntityFromRequest(ClientRequest request, @MappingTarget Client client);

        List<ClientResponse> toResponseList(List<Client> clients);
}
