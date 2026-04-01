package com.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.dto.VacancyRequest;
import com.erp.dto.VacancyResponse;
import com.erp.entity.Vacancy;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.VacancyRepository;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public List<VacancyResponse> findAll() {
        return vacancyRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public VacancyResponse findById(String vacancyId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new EntityNotFoundException("Vacancy not found: " + vacancyId));
        return toResponse(vacancy);
    }

    public VacancyResponse create(VacancyRequest request) {
        Vacancy vacancy = toEntity(request);
        return toResponse(vacancyRepository.save(vacancy));
    }

    public VacancyResponse update(String vacancyId, VacancyRequest request) {
        if (!vacancyRepository.existsById(vacancyId)) {
            throw new EntityNotFoundException("Vacancy not found: " + vacancyId);
        }
        Vacancy vacancy = toEntity(request);
        vacancy.setVacancyId(vacancyId);
        return toResponse(vacancyRepository.save(vacancy));
    }

    public void delete(String vacancyId) {
        if (!vacancyRepository.existsById(vacancyId)) {
            throw new EntityNotFoundException("Vacancy not found: " + vacancyId);
        }
        vacancyRepository.deleteById(vacancyId);
    }

    private Vacancy toEntity(VacancyRequest request) {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId(request.getVacancyId());
        vacancy.setClientId(request.getClientId());
        vacancy.setDateVacancyPublishzhed(request.getDateVacancyPublishzhed());
        vacancy.setDateVacancyFilled(request.getDateVacancyFilled());
        vacancy.setFeePayable(request.getFeePayable());
        vacancy.setDateFeePaid(request.getDateFeePaid());
        vacancy.setVacanciesVacancyId(request.getVacanciesVacancyId());
        return vacancy;
    }

    private VacancyResponse toResponse(Vacancy vacancy) {
        return new VacancyResponse(
                vacancy.getVacancyId(),
                vacancy.getClientId(),
                vacancy.getDateVacancyPublishzhed(),
                vacancy.getDateVacancyFilled(),
                vacancy.getFeePayable(),
                vacancy.getDateFeePaid(),
                vacancy.getVacanciesVacancyId()
        );
    }
}
