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
import com.erp.entity.RecruitmentAgency;
import com.erp.repository.RecruitmentAgencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecruitmentAgencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecruitmentAgencyRepository recruitmentAgencyRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        recruitmentAgencyRepository.deleteAll();
        token = jwtUtil.generateToken("testuser");
    }

    @Test
    void findAll_returnsAgencies() throws Exception {
        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId("AG001");
        agency.setAgecnyName("Top Recruiters");
        recruitmentAgencyRepository.save(agency);

        mockMvc.perform(get("/api/agencies")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].agencyId").value("AG001"));
    }

    @Test
    void findById_success() throws Exception {
        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId("AG001");
        agency.setAgecnyName("Top Recruiters");
        recruitmentAgencyRepository.save(agency);

        mockMvc.perform(get("/api/agencies/AG001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agecnyName").value("Top Recruiters"));
    }

    @Test
    void findById_notFound() throws Exception {
        mockMvc.perform(get("/api/agencies/NONEXISTENT")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_success() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "agencyId", "AG001",
                "agecnyName", "Top Recruiters",
                "contactName", "John Doe"));

        mockMvc.perform(post("/api/agencies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agencyId").value("AG001"));
    }

    @Test
    void create_missingRequired_returnsBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "agecnyName", "Top Recruiters"));

        mockMvc.perform(post("/api/agencies")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_success() throws Exception {
        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId("AG001");
        agency.setAgecnyName("Top Recruiters");
        recruitmentAgencyRepository.save(agency);

        String body = objectMapper.writeValueAsString(java.util.Map.of(
                "agencyId", "AG001",
                "agecnyName", "Updated Agency"));

        mockMvc.perform(put("/api/agencies/AG001")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agecnyName").value("Updated Agency"));
    }

    @Test
    void delete_success() throws Exception {
        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId("AG001");
        agency.setAgecnyName("Top Recruiters");
        recruitmentAgencyRepository.save(agency);

        mockMvc.perform(delete("/api/agencies/AG001")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
