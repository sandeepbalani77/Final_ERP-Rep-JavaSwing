package com.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.dto.OfficeRequest;
import com.erp.dto.OfficeResponse;
import com.erp.entity.Office;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.OfficeRepository;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public List<OfficeResponse> findAll() {
        return officeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public OfficeResponse findById(String officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new EntityNotFoundException("Office not found: " + officeId));
        return toResponse(office);
    }

    public OfficeResponse create(OfficeRequest request) {
        Office office = toEntity(request);
        return toResponse(officeRepository.save(office));
    }

    public OfficeResponse update(String officeId, OfficeRequest request) {
        if (!officeRepository.existsById(officeId)) {
            throw new EntityNotFoundException("Office not found: " + officeId);
        }
        Office office = toEntity(request);
        office.setOfficeId(officeId);
        return toResponse(officeRepository.save(office));
    }

    public void delete(String officeId) {
        if (!officeRepository.existsById(officeId)) {
            throw new EntityNotFoundException("Office not found: " + officeId);
        }
        officeRepository.deleteById(officeId);
    }

    private Office toEntity(OfficeRequest request) {
        Office office = new Office();
        office.setOfficeId(request.getOfficeId());
        office.setOfficeName(request.getOfficeName());
        office.setOfficeDetails(request.getOfficeDetails());
        office.setAddressId(request.getAddressId());
        office.setAgencyId(request.getAgencyId());
        return office;
    }

    private OfficeResponse toResponse(Office office) {
        return new OfficeResponse(
                office.getOfficeId(),
                office.getOfficeName(),
                office.getOfficeDetails(),
                office.getAddressId(),
                office.getAgencyId()
        );
    }
}
