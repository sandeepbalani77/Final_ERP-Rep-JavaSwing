package com.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.dto.CandidateRequest;
import com.erp.dto.CandidateResponse;
import com.erp.entity.Candidate;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.CandidateRepository;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<CandidateResponse> findAll() {
        return candidateRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CandidateResponse findById(String candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + candidateId));
        return toResponse(candidate);
    }

    public CandidateResponse create(CandidateRequest request) {
        Candidate candidate = toEntity(request);
        return toResponse(candidateRepository.save(candidate));
    }

    public CandidateResponse update(String candidateId, CandidateRequest request) {
        if (!candidateRepository.existsById(candidateId)) {
            throw new EntityNotFoundException("Candidate not found: " + candidateId);
        }
        Candidate candidate = toEntity(request);
        candidate.setCandidateId(candidateId);
        return toResponse(candidateRepository.save(candidate));
    }

    public void delete(String candidateId) {
        if (!candidateRepository.existsById(candidateId)) {
            throw new EntityNotFoundException("Candidate not found: " + candidateId);
        }
        candidateRepository.deleteById(candidateId);
    }

    private Candidate toEntity(CandidateRequest request) {
        Candidate candidate = new Candidate();
        candidate.setCandidateId(request.getCandidateId());
        candidate.setAddressId(request.getAddressId());
        candidate.setOfficeId(request.getOfficeId());
        candidate.setRegistrationDate(request.getRegistrationDate());
        candidate.setFirstName(request.getFirstName());
        candidate.setEmailAddress(request.getEmailAddress());
        candidate.setHomePhoneNumber(request.getHomePhoneNumber());
        candidate.setCandidatesCandidateId(request.getCandidatesCandidateId());
        return candidate;
    }

    private CandidateResponse toResponse(Candidate candidate) {
        return new CandidateResponse(
                candidate.getCandidateId(),
                candidate.getAddressId(),
                candidate.getOfficeId(),
                candidate.getRegistrationDate(),
                candidate.getFirstName(),
                candidate.getEmailAddress(),
                candidate.getHomePhoneNumber(),
                candidate.getCandidatesCandidateId()
        );
    }
}
