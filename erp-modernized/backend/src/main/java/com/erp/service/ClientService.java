package com.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.dto.ClientRequest;
import com.erp.dto.ClientResponse;
import com.erp.entity.Client;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ClientResponse findById(String clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: " + clientId));
        return toResponse(client);
    }

    public ClientResponse create(ClientRequest request) {
        Client client = toEntity(request);
        return toResponse(clientRepository.save(client));
    }

    public ClientResponse update(String clientId, ClientRequest request) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException("Client not found: " + clientId);
        }
        Client client = toEntity(request);
        client.setClientId(clientId);
        return toResponse(clientRepository.save(client));
    }

    public void delete(String clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException("Client not found: " + clientId);
        }
        clientRepository.deleteById(clientId);
    }

    private Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setClientName(request.getClientName());
        client.setClientId(request.getClientId());
        client.setClientName1(request.getClientName1());
        client.setEmailAddress(request.getEmailAddress());
        client.setCellMobilePhoneNumber(request.getCellMobilePhoneNumber());
        client.setDateFirstContact(request.getDateFirstContact());
        client.setAddressId(request.getAddressId());
        client.setOfficeId(request.getOfficeId());
        client.setClientsClientId(request.getClientsClientId());
        return client;
    }

    private ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getClientName(),
                client.getClientId(),
                client.getClientName1(),
                client.getEmailAddress(),
                client.getCellMobilePhoneNumber(),
                client.getDateFirstContact(),
                client.getAddressId(),
                client.getOfficeId(),
                client.getClientsClientId()
        );
    }
}
