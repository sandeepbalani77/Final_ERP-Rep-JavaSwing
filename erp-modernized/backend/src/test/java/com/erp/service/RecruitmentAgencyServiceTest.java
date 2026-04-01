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

import com.erp.dto.RecruitmentAgencyRequest;
import com.erp.dto.RecruitmentAgencyResponse;
import com.erp.entity.RecruitmentAgency;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.RecruitmentAgencyRepository;

@ExtendWith(MockitoExtension.class)
class RecruitmentAgencyServiceTest {

    @Mock
    private RecruitmentAgencyRepository recruitmentAgencyRepository;

    @InjectMocks
    private RecruitmentAgencyService recruitmentAgencyService;

    private RecruitmentAgency createAgency(String id, String name) {
        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId(id);
        agency.setAgecnyName(name);
        agency.setContactName("John Doe");
        agency.setContactGenderMf("M");
        agency.setContactJobTitle("Manager");
        agency.setCellMobilePhone("1234567890");
        agency.setEmailAddress("john@test.com");
        agency.setWebAddress("http://test.com");
        return agency;
    }

    private RecruitmentAgencyRequest createRequest(String id) {
        RecruitmentAgencyRequest request = new RecruitmentAgencyRequest();
        request.setAgencyId(id);
        request.setAgecnyName("Top Recruiters");
        request.setContactName("John Doe");
        return request;
    }

    @Test
    void findAll_returnsAllAgencies() {
        when(recruitmentAgencyRepository.findAll()).thenReturn(List.of(
                createAgency("AG001", "Agency One"), createAgency("AG002", "Agency Two")));

        List<RecruitmentAgencyResponse> result = recruitmentAgencyService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_success() {
        when(recruitmentAgencyRepository.findById("AG001")).thenReturn(Optional.of(createAgency("AG001", "Top Recruiters")));

        RecruitmentAgencyResponse result = recruitmentAgencyService.findById("AG001");

        assertThat(result.getAgecnyName()).isEqualTo("Top Recruiters");
    }

    @Test
    void findById_notFound_throwsException() {
        when(recruitmentAgencyRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recruitmentAgencyService.findById("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void create_savesAndReturnsResponse() {
        RecruitmentAgency saved = createAgency("AG001", "Top Recruiters");
        when(recruitmentAgencyRepository.save(any(RecruitmentAgency.class))).thenReturn(saved);

        RecruitmentAgencyResponse result = recruitmentAgencyService.create(createRequest("AG001"));

        assertThat(result.getAgencyId()).isEqualTo("AG001");
        verify(recruitmentAgencyRepository).save(any(RecruitmentAgency.class));
    }

    @Test
    void update_success() {
        when(recruitmentAgencyRepository.existsById("AG001")).thenReturn(true);
        RecruitmentAgency saved = createAgency("AG001", "Top Recruiters");
        when(recruitmentAgencyRepository.save(any(RecruitmentAgency.class))).thenReturn(saved);

        RecruitmentAgencyResponse result = recruitmentAgencyService.update("AG001", createRequest("AG001"));

        assertThat(result.getAgencyId()).isEqualTo("AG001");
    }

    @Test
    void update_notFound_throwsException() {
        when(recruitmentAgencyRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> recruitmentAgencyService.update("NONEXISTENT", createRequest("NONEXISTENT")))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(recruitmentAgencyRepository.existsById("AG001")).thenReturn(true);

        recruitmentAgencyService.delete("AG001");

        verify(recruitmentAgencyRepository).deleteById("AG001");
    }

    @Test
    void delete_notFound_throwsException() {
        when(recruitmentAgencyRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> recruitmentAgencyService.delete("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
