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

import com.erp.dto.CandidateRequest;
import com.erp.dto.CandidateResponse;
import com.erp.entity.Candidate;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.CandidateRepository;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateService candidateService;

    private Candidate createCandidate(String id, String name) {
        Candidate candidate = new Candidate();
        candidate.setCandidateId(id);
        candidate.setFirstName(name);
        candidate.setEmailAddress("test@test.com");
        candidate.setHomePhoneNumber("1234567890");
        candidate.setRegistrationDate("2024-01-15");
        return candidate;
    }

    private CandidateRequest createRequest(String id) {
        CandidateRequest request = new CandidateRequest();
        request.setCandidateId(id);
        request.setFirstName("Jane");
        request.setEmailAddress("jane@test.com");
        return request;
    }

    @Test
    void findAll_returnsAllCandidates() {
        when(candidateRepository.findAll()).thenReturn(List.of(
                createCandidate("CAND001", "Jane"), createCandidate("CAND002", "John")));

        List<CandidateResponse> result = candidateService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_success() {
        when(candidateRepository.findById("CAND001")).thenReturn(Optional.of(createCandidate("CAND001", "Jane")));

        CandidateResponse result = candidateService.findById("CAND001");

        assertThat(result.getFirstName()).isEqualTo("Jane");
    }

    @Test
    void findById_notFound_throwsException() {
        when(candidateRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> candidateService.findById("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void create_savesAndReturnsResponse() {
        Candidate saved = createCandidate("CAND001", "Jane");
        when(candidateRepository.save(any(Candidate.class))).thenReturn(saved);

        CandidateResponse result = candidateService.create(createRequest("CAND001"));

        assertThat(result.getCandidateId()).isEqualTo("CAND001");
        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    void update_success() {
        when(candidateRepository.existsById("CAND001")).thenReturn(true);
        Candidate saved = createCandidate("CAND001", "Jane");
        when(candidateRepository.save(any(Candidate.class))).thenReturn(saved);

        CandidateResponse result = candidateService.update("CAND001", createRequest("CAND001"));

        assertThat(result.getCandidateId()).isEqualTo("CAND001");
    }

    @Test
    void update_notFound_throwsException() {
        when(candidateRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> candidateService.update("NONEXISTENT", createRequest("NONEXISTENT")))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(candidateRepository.existsById("CAND001")).thenReturn(true);

        candidateService.delete("CAND001");

        verify(candidateRepository).deleteById("CAND001");
    }

    @Test
    void delete_notFound_throwsException() {
        when(candidateRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> candidateService.delete("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
