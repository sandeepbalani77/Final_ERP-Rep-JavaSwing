package com.erp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.erp.config.JwtUtil;
import com.erp.entity.Client;
import com.erp.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
        token = jwtUtil.generateToken("testuser");
    }

    @Test
    void findAll_returnsClients() throws Exception {
        Client client = new Client();
        client.setClientId("CL001");
        client.setClientName("Acme Corp");
        clientRepository.save(client);

        mockMvc.perform(get("/api/clients")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientId").value("CL001"));
    }

    @Test
    void findById_success() throws Exception {
        Client client = new Client();
        client.setClientId("CL001");
        client.setClientName("Acme Corp");
        clientRepository.save(client);

        mockMvc.perform(get("/api/clients/CL001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("Acme Corp"));
    }

    @Test
    void findById_notFound() throws Exception {
        mockMvc.perform(get("/api/clients/NONEXISTENT")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_success() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "clientId", "CL001",
                "clientName", "Acme Corp",
                "emailAddress", "acme@test.com"));

        mockMvc.perform(post("/api/clients")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId").value("CL001"));
    }

    @Test
    void create_missingRequired_returnsBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "emailAddress", "acme@test.com"));

        mockMvc.perform(post("/api/clients")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_success() throws Exception {
        Client client = new Client();
        client.setClientId("CL001");
        client.setClientName("Acme Corp");
        clientRepository.save(client);

        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "clientId", "CL001",
                "clientName", "Acme Updated"));

        mockMvc.perform(put("/api/clients/CL001")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("Acme Updated"));
    }

    @Test
    void delete_success() throws Exception {
        Client client = new Client();
        client.setClientId("CL001");
        client.setClientName("Acme Corp");
        clientRepository.save(client);

        mockMvc.perform(delete("/api/clients/CL001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
