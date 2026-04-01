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

import com.erp.dto.OfficeRequest;
import com.erp.dto.OfficeResponse;
import com.erp.entity.Office;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.OfficeRepository;

@ExtendWith(MockitoExtension.class)
class OfficeServiceTest {

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private OfficeService officeService;

    private Office createOffice(String id, String name) {
        Office office = new Office();
        office.setOfficeId(id);
        office.setOfficeName(name);
        office.setOfficeDetails("Details");
        office.setAddressId("ADDR001");
        office.setAgencyId("AG001");
        return office;
    }

    private OfficeRequest createRequest(String id) {
        OfficeRequest request = new OfficeRequest();
        request.setOfficeId(id);
        request.setOfficeName("Main Office");
        request.setOfficeDetails("HQ");
        request.setAddressId("ADDR001");
        request.setAgencyId("AG001");
        return request;
    }

    @Test
    void findAll_returnsAllOffices() {
        when(officeRepository.findAll()).thenReturn(List.of(
                createOffice("OFF001", "Office One"), createOffice("OFF002", "Office Two")));

        List<OfficeResponse> result = officeService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_success() {
        when(officeRepository.findById("OFF001")).thenReturn(Optional.of(createOffice("OFF001", "Main Office")));

        OfficeResponse result = officeService.findById("OFF001");

        assertThat(result.getOfficeName()).isEqualTo("Main Office");
    }

    @Test
    void findById_notFound_throwsException() {
        when(officeRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> officeService.findById("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void create_savesAndReturnsResponse() {
        Office saved = createOffice("OFF001", "Main Office");
        when(officeRepository.save(any(Office.class))).thenReturn(saved);

        OfficeResponse result = officeService.create(createRequest("OFF001"));

        assertThat(result.getOfficeId()).isEqualTo("OFF001");
        verify(officeRepository).save(any(Office.class));
    }

    @Test
    void update_success() {
        when(officeRepository.existsById("OFF001")).thenReturn(true);
        Office saved = createOffice("OFF001", "Main Office");
        when(officeRepository.save(any(Office.class))).thenReturn(saved);

        OfficeResponse result = officeService.update("OFF001", createRequest("OFF001"));

        assertThat(result.getOfficeId()).isEqualTo("OFF001");
    }

    @Test
    void update_notFound_throwsException() {
        when(officeRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> officeService.update("NONEXISTENT", createRequest("NONEXISTENT")))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(officeRepository.existsById("OFF001")).thenReturn(true);

        officeService.delete("OFF001");

        verify(officeRepository).deleteById("OFF001");
    }

    @Test
    void delete_notFound_throwsException() {
        when(officeRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> officeService.delete("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
