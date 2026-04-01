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
import com.erp.entity.Candidate;
import com.erp.repository.CandidateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        candidateRepository.deleteAll();
        token = jwtUtil.generateToken("testuser");
    }

    @Test
    void findAll_returnsCandidates() throws Exception {
        Candidate candidate = new Candidate();
        candidate.setCandidateId("CAND001");
        candidate.setFirstName("Jane");
        candidateRepository.save(candidate);

        mockMvc.perform(get("/api/candidates")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].candidateId").value("CAND001"));
    }

    @Test
    void findById_success() throws Exception {
        Candidate candidate = new Candidate();
        candidate.setCandidateId("CAND001");
        candidate.setFirstName("Jane");
        candidateRepository.save(candidate);

        mockMvc.perform(get("/api/candidates/CAND001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void findById_notFound() throws Exception {
        mockMvc.perform(get("/api/candidates/NONEXISTENT")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_success() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "candidateId", "CAND001",
                "firstName", "Jane",
                "emailAddress", "jane@test.com"));

        mockMvc.perform(post("/api/candidates")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.candidateId").value("CAND001"));
    }

    @Test
    void create_missingRequired_returnsBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "firstName", "Jane"));

        mockMvc.perform(post("/api/candidates")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_success() throws Exception {
        Candidate candidate = new Candidate();
        candidate.setCandidateId("CAND001");
        candidate.setFirstName("Jane");
        candidateRepository.save(candidate);

        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "candidateId", "CAND001",
                "firstName", "Jane Updated"));

        mockMvc.perform(put("/api/candidates/CAND001")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane Updated"));
    }

    @Test
    void delete_success() throws Exception {
        Candidate candidate = new Candidate();
        candidate.setCandidateId("CAND001");
        candidate.setFirstName("Jane");
        candidateRepository.save(candidate);

        mockMvc.perform(delete("/api/candidates/CAND001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
