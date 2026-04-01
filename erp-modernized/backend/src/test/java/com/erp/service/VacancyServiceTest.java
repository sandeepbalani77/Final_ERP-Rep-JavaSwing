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

import com.erp.dto.VacancyRequest;
import com.erp.dto.VacancyResponse;
import com.erp.entity.Vacancy;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.VacancyRepository;

@ExtendWith(MockitoExtension.class)
class VacancyServiceTest {

    @Mock
    private VacancyRepository vacancyRepository;

    @InjectMocks
    private VacancyService vacancyService;

    private Vacancy createVacancy(String id) {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId(id);
        vacancy.setClientId("CL001");
        vacancy.setDateVacancyPublishzhed("2024-02-01");
        vacancy.setDateVacancyFilled("2024-03-01");
        vacancy.setFeePayable("5000");
        vacancy.setDateFeePaid("2024-03-15");
        return vacancy;
    }

    private VacancyRequest createRequest(String id) {
        VacancyRequest request = new VacancyRequest();
        request.setVacancyId(id);
        request.setClientId("CL001");
        request.setDateVacancyPublishzhed("2024-02-01");
        return request;
    }

    @Test
    void findAll_returnsAllVacancies() {
        when(vacancyRepository.findAll()).thenReturn(List.of(
                createVacancy("VAC001"), createVacancy("VAC002")));

        List<VacancyResponse> result = vacancyService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_success() {
        when(vacancyRepository.findById("VAC001")).thenReturn(Optional.of(createVacancy("VAC001")));

        VacancyResponse result = vacancyService.findById("VAC001");

        assertThat(result.getVacancyId()).isEqualTo("VAC001");
    }

    @Test
    void findById_notFound_throwsException() {
        when(vacancyRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vacancyService.findById("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void create_savesAndReturnsResponse() {
        Vacancy saved = createVacancy("VAC001");
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(saved);

        VacancyResponse result = vacancyService.create(createRequest("VAC001"));

        assertThat(result.getVacancyId()).isEqualTo("VAC001");
        verify(vacancyRepository).save(any(Vacancy.class));
    }

    @Test
    void update_success() {
        when(vacancyRepository.existsById("VAC001")).thenReturn(true);
        Vacancy saved = createVacancy("VAC001");
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(saved);

        VacancyResponse result = vacancyService.update("VAC001", createRequest("VAC001"));

        assertThat(result.getVacancyId()).isEqualTo("VAC001");
    }

    @Test
    void update_notFound_throwsException() {
        when(vacancyRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> vacancyService.update("NONEXISTENT", createRequest("NONEXISTENT")))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(vacancyRepository.existsById("VAC001")).thenReturn(true);

        vacancyService.delete("VAC001");

        verify(vacancyRepository).deleteById("VAC001");
    }

    @Test
    void delete_notFound_throwsException() {
        when(vacancyRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> vacancyService.delete("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
