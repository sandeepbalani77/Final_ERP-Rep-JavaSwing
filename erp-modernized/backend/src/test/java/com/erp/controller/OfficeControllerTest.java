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
import com.erp.entity.Office;
import com.erp.repository.OfficeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OfficeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        officeRepository.deleteAll();
        token = jwtUtil.generateToken("testuser");
    }

    @Test
    void findAll_returnsOffices() throws Exception {
        Office office = new Office();
        office.setOfficeId("OFF001");
        office.setOfficeName("Main Office");
        officeRepository.save(office);

        mockMvc.perform(get("/api/offices")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].officeId").value("OFF001"));
    }

    @Test
    void findById_success() throws Exception {
        Office office = new Office();
        office.setOfficeId("OFF001");
        office.setOfficeName("Main Office");
        officeRepository.save(office);

        mockMvc.perform(get("/api/offices/OFF001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeName").value("Main Office"));
    }

    @Test
    void findById_notFound() throws Exception {
        mockMvc.perform(get("/api/offices/NONEXISTENT")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_success() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "officeId", "OFF001",
                "officeName", "Main Office",
                "officeDetails", "Headquarters"));

        mockMvc.perform(post("/api/offices")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.officeId").value("OFF001"));
    }

    @Test
    void create_missingRequired_returnsBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "officeName", "Main Office"));

        mockMvc.perform(post("/api/offices")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_success() throws Exception {
        Office office = new Office();
        office.setOfficeId("OFF001");
        office.setOfficeName("Main Office");
        officeRepository.save(office);

        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "officeId", "OFF001",
                "officeName", "Updated Office"));

        mockMvc.perform(put("/api/offices/OFF001")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeName").value("Updated Office"));
    }

    @Test
    void delete_success() throws Exception {
        Office office = new Office();
        office.setOfficeId("OFF001");
        office.setOfficeName("Main Office");
        officeRepository.save(office);

        mockMvc.perform(delete("/api/offices/OFF001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
