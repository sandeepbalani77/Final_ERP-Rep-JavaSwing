package com.erp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erp.dto.ClientRequest;
import com.erp.dto.ClientResponse;
import com.erp.entity.Client;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client createClient(String id, String name) {
        Client client = new Client();
        client.setClientId(id);
        client.setClientName(name);
        client.setClientName1("Alt Name");
        client.setEmailAddress("test@test.com");
        client.setCellMobilePhoneNumber("1234567890");
        client.setDateFirstContact("2024-01-01");
        return client;
    }

    private ClientRequest createRequest(String id) {
        ClientRequest request = new ClientRequest();
        request.setClientId(id);
        request.setClientName("Acme Corp");
        request.setClientName1("Acme");
        request.setEmailAddress("acme@test.com");
        return request;
    }

    @Test
    void findAll_returnsAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(
                createClient("CL001", "Acme"), createClient("CL002", "Beta")));

        List<ClientResponse> result = clientService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_success() {
        when(clientRepository.findById("CL001")).thenReturn(Optional.of(createClient("CL001", "Acme")));

        ClientResponse result = clientService.findById("CL001");

        assertThat(result.getClientName()).isEqualTo("Acme");
    }

    @Test
    void findById_notFound_throwsException() {
        when(clientRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.findById("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void create_savesAndReturnsResponse() {
        Client saved = createClient("CL001", "Acme Corp");
        when(clientRepository.save(any(Client.class))).thenReturn(saved);

        ClientResponse result = clientService.create(createRequest("CL001"));

        assertThat(result.getClientId()).isEqualTo("CL001");
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void update_success() {
        when(clientRepository.existsById("CL001")).thenReturn(true);
        Client saved = createClient("CL001", "Acme Corp");
        when(clientRepository.save(any(Client.class))).thenReturn(saved);

        ClientResponse result = clientService.update("CL001", createRequest("CL001"));

        assertThat(result.getClientId()).isEqualTo("CL001");
    }

    @Test
    void update_notFound_throwsException() {
        when(clientRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> clientService.update("NONEXISTENT", createRequest("NONEXISTENT")))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(clientRepository.existsById("CL001")).thenReturn(true);

        clientService.delete("CL001");

        verify(clientRepository).deleteById("CL001");
    }

    @Test
    void delete_notFound_throwsException() {
        when(clientRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> clientService.delete("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
