package com.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.dto.RecruitmentAgencyRequest;
import com.erp.dto.RecruitmentAgencyResponse;
import com.erp.entity.RecruitmentAgency;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.RecruitmentAgencyRepository;

@Service
public class RecruitmentAgencyService {

    private final RecruitmentAgencyRepository recruitmentAgencyRepository;

    public RecruitmentAgencyService(RecruitmentAgencyRepository recruitmentAgencyRepository) {
        this.recruitmentAgencyRepository = recruitmentAgencyRepository;
    }

    public List<RecruitmentAgencyResponse> findAll() {
        return recruitmentAgencyRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public RecruitmentAgencyResponse findById(String agencyId) {
        RecruitmentAgency agency = recruitmentAgencyRepository.findById(agencyId)
                .orElseThrow(() -> new EntityNotFoundException("Recruitment Agency not found: " + agencyId));
        return toResponse(agency);
    }

    public RecruitmentAgencyResponse create(RecruitmentAgencyRequest request) {
        RecruitmentAgency agency = toEntity(request);
        return toResponse(recruitmentAgencyRepository.save(agency));
    }

    public RecruitmentAgencyResponse update(String agencyId, RecruitmentAgencyRequest request) {
        if (!recruitmentAgencyRepository.existsById(agencyId)) {
            throw new EntityNotFoundException("Recruitment Agency not found: " + agencyId);
        }
        RecruitmentAgency agency = toEntity(request);
        agency.setAgencyId(agencyId);
        return toResponse(recruitmentAgencyRepository.save(agency));
    }

    public void delete(String agencyId) {
        if (!recruitmentAgencyRepository.existsById(agencyId)) {
            throw new EntityNotFoundException("Recruitment Agency not found: " + agencyId);
        }
        recruitmentAgencyRepository.deleteById(agencyId);
    }

    private RecruitmentAgency toEntity(RecruitmentAgencyRequest request) {
        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId(request.getAgencyId());
        agency.setAgecnyName(request.getAgecnyName());
        agency.setContactName(request.getContactName());
        agency.setContactGenderMf(request.getContactGenderMf());
        agency.setContactJobTitle(request.getContactJobTitle());
        agency.setCellMobilePhone(request.getCellMobilePhone());
        agency.setEmailAddress(request.getEmailAddress());
        agency.setWebAddress(request.getWebAddress());
        return agency;
    }

    private RecruitmentAgencyResponse toResponse(RecruitmentAgency agency) {
        return new RecruitmentAgencyResponse(
                agency.getAgencyId(),
                agency.getAgecnyName(),
                agency.getContactName(),
                agency.getContactGenderMf(),
                agency.getContactJobTitle(),
                agency.getCellMobilePhone(),
                agency.getEmailAddress(),
                agency.getWebAddress()
        );
    }
}
