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
import com.erp.entity.Address;
import com.erp.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        token = jwtUtil.generateToken("testuser");
    }

    @Test
    void findAll_returnsAddresses() throws Exception {
        Address addr = new Address();
        addr.setAddressId("ADDR001");
        addr.setCityTown("New York");
        addr.setCountry("USA");
        addressRepository.save(addr);

        mockMvc.perform(get("/api/addresses")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].addressId").value("ADDR001"));
    }

    @Test
    void findById_success() throws Exception {
        Address addr = new Address();
        addr.setAddressId("ADDR001");
        addr.setCityTown("New York");
        addressRepository.save(addr);

        mockMvc.perform(get("/api/addresses/ADDR001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityTown").value("New York"));
    }

    @Test
    void findById_notFound() throws Exception {
        mockMvc.perform(get("/api/addresses/NONEXISTENT")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_success() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "addressId", "ADDR001",
                "cityTown", "New York",
                "stateCountryProvince", "NY",
                "zip", "10001",
                "country", "USA"));

        mockMvc.perform(post("/api/addresses")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.addressId").value("ADDR001"));
    }

    @Test
    void create_missingId_returnsBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "cityTown", "New York"));

        mockMvc.perform(post("/api/addresses")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_success() throws Exception {
        Address addr = new Address();
        addr.setAddressId("ADDR001");
        addr.setCityTown("New York");
        addressRepository.save(addr);

        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "addressId", "ADDR001",
                "cityTown", "Los Angeles",
                "country", "USA"));

        mockMvc.perform(put("/api/addresses/ADDR001")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityTown").value("Los Angeles"));
    }

    @Test
    void delete_success() throws Exception {
        Address addr = new Address();
        addr.setAddressId("ADDR001");
        addressRepository.save(addr);

        mockMvc.perform(delete("/api/addresses/ADDR001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/addresses"))
                .andExpect(status().isForbidden());
    }
}
