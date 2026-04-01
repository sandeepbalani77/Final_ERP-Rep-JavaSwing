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
import com.erp.entity.Vacancy;
import com.erp.repository.VacancyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VacancyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        vacancyRepository.deleteAll();
        token = jwtUtil.generateToken("testuser");
    }

    @Test
    void findAll_returnsVacancies() throws Exception {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId("VAC001");
        vacancy.setFeePayable("5000");
        vacancyRepository.save(vacancy);

        mockMvc.perform(get("/api/vacancies")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vacancyId").value("VAC001"));
    }

    @Test
    void findById_success() throws Exception {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId("VAC001");
        vacancy.setFeePayable("5000");
        vacancyRepository.save(vacancy);

        mockMvc.perform(get("/api/vacancies/VAC001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feePayable").value("5000"));
    }

    @Test
    void findById_notFound() throws Exception {
        mockMvc.perform(get("/api/vacancies/NONEXISTENT")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_success() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "vacancyId", "VAC001",
                "dateVacancyPublishzhed", "2024-02-01",
                "feePayable", "5000"));

        mockMvc.perform(post("/api/vacancies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vacancyId").value("VAC001"));
    }

    @Test
    void create_missingRequired_returnsBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "feePayable", "5000"));

        mockMvc.perform(post("/api/vacancies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_success() throws Exception {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId("VAC001");
        vacancy.setFeePayable("5000");
        vacancyRepository.save(vacancy);

        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "vacancyId", "VAC001",
                "feePayable", "7500"));

        mockMvc.perform(put("/api/vacancies/VAC001")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feePayable").value("7500"));
    }

    @Test
    void delete_success() throws Exception {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId("VAC001");
        vacancyRepository.save(vacancy);

        mockMvc.perform(delete("/api/vacancies/VAC001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
