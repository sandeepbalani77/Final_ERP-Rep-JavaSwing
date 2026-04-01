package com.erp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.erp.entity.Client;
import com.erp.entity.Vacancy;

@DataJpaTest
@ActiveProfiles("test")
class VacancyRepositoryTest {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setClientId("CL001");
        client.setClientName("Acme Corp");
        clientRepository.save(client);
    }

    @Test
    void testSaveAndFindById() {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId("VAC001");
        vacancy.setClientId("CL001");
        vacancy.setDateVacancyPublishzhed("2024-02-01");
        vacancy.setDateVacancyFilled("2024-03-01");
        vacancy.setFeePayable("5000");
        vacancy.setDateFeePaid("2024-03-15");

        vacancyRepository.save(vacancy);

        Optional<Vacancy> found = vacancyRepository.findById("VAC001");
        assertThat(found).isPresent();
        assertThat(found.get().getClientId()).isEqualTo("CL001");
        assertThat(found.get().getDateVacancyPublishzhed()).isEqualTo("2024-02-01");
    }

    @Test
    void testFindAll() {
        Vacancy v1 = new Vacancy();
        v1.setVacancyId("VAC001");
        v1.setClientId("CL001");

        Vacancy v2 = new Vacancy();
        v2.setVacancyId("VAC002");
        v2.setClientId("CL001");

        vacancyRepository.save(v1);
        vacancyRepository.save(v2);

        List<Vacancy> all = vacancyRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void testDeleteById() {
        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyId("VAC001");
        vacancyRepository.save(vacancy);

        vacancyRepository.deleteById("VAC001");

        Optional<Vacancy> found = vacancyRepository.findById("VAC001");
        assertThat(found).isEmpty();
    }

    @Test
    void testSelfReferentialForeignKey() {
        Vacancy parent = new Vacancy();
        parent.setVacancyId("VAC001");
        vacancyRepository.save(parent);

        Vacancy child = new Vacancy();
        child.setVacancyId("VAC002");
        child.setVacanciesVacancyId("VAC001");
        vacancyRepository.save(child);

        Optional<Vacancy> found = vacancyRepository.findById("VAC002");
        assertThat(found).isPresent();
        assertThat(found.get().getVacanciesVacancyId()).isEqualTo("VAC001");
    }
}
