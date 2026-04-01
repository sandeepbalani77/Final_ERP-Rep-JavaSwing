package com.erp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.erp.entity.RecruitmentAgency;

@DataJpaTest
@ActiveProfiles("test")
class RecruitmentAgencyRepositoryTest {

    @Autowired
    private RecruitmentAgencyRepository recruitmentAgencyRepository;

    @Test
    void testSaveAndFindById() {
        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId("AG001");
        agency.setAgecnyName("Top Recruiters");
        agency.setContactName("John Doe");
        agency.setContactGenderMf("M");
        agency.setContactJobTitle("Manager");
        agency.setCellMobilePhone("1234567890");
        agency.setEmailAddress("john@top.com");
        agency.setWebAddress("http://top.com");

        recruitmentAgencyRepository.save(agency);

        Optional<RecruitmentAgency> found = recruitmentAgencyRepository.findById("AG001");
        assertThat(found).isPresent();
        assertThat(found.get().getAgecnyName()).isEqualTo("Top Recruiters");
        assertThat(found.get().getContactName()).isEqualTo("John Doe");
    }

    @Test
    void testFindAll() {
        RecruitmentAgency ag1 = new RecruitmentAgency();
        ag1.setAgencyId("AG001");
        ag1.setAgecnyName("Agency One");

        RecruitmentAgency ag2 = new RecruitmentAgency();
        ag2.setAgencyId("AG002");
        ag2.setAgecnyName("Agency Two");

        recruitmentAgencyRepository.save(ag1);
        recruitmentAgencyRepository.save(ag2);

        List<RecruitmentAgency> all = recruitmentAgencyRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void testDeleteById() {
        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId("AG001");
        agency.setAgecnyName("Top Recruiters");
        recruitmentAgencyRepository.save(agency);

        recruitmentAgencyRepository.deleteById("AG001");

        Optional<RecruitmentAgency> found = recruitmentAgencyRepository.findById("AG001");
        assertThat(found).isEmpty();
    }

    @Test
    void testFindByIdNotFound() {
        Optional<RecruitmentAgency> found = recruitmentAgencyRepository.findById("NONEXISTENT");
        assertThat(found).isEmpty();
    }
}
