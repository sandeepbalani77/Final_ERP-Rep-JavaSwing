package com.erp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.erp.entity.Address;
import com.erp.entity.Candidate;
import com.erp.entity.Office;
import com.erp.entity.RecruitmentAgency;

@DataJpaTest
@ActiveProfiles("test")
class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private RecruitmentAgencyRepository recruitmentAgencyRepository;

    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setAddressId("ADDR001");
        address.setCityTown("New York");
        addressRepository.save(address);

        RecruitmentAgency agency = new RecruitmentAgency();
        agency.setAgencyId("AG001");
        agency.setAgecnyName("Top Recruiters");
        recruitmentAgencyRepository.save(agency);

        Office office = new Office();
        office.setOfficeId("OFF001");
        office.setOfficeName("Main Office");
        office.setAddressId("ADDR001");
        office.setAgencyId("AG001");
        officeRepository.save(office);
    }

    @Test
    void testSaveAndFindById() {
        Candidate candidate = new Candidate();
        candidate.setCandidateId("CAND001");
        candidate.setAddressId("ADDR001");
        candidate.setOfficeId("OFF001");
        candidate.setRegistrationDate("2024-01-15");
        candidate.setFirstName("Jane");
        candidate.setEmailAddress("jane@test.com");
        candidate.setHomePhoneNumber("9876543210");

        candidateRepository.save(candidate);

        Optional<Candidate> found = candidateRepository.findById("CAND001");
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Jane");
        assertThat(found.get().getOfficeId()).isEqualTo("OFF001");
    }

    @Test
    void testFindAll() {
        Candidate c1 = new Candidate();
        c1.setCandidateId("CAND001");
        c1.setFirstName("Jane");

        Candidate c2 = new Candidate();
        c2.setCandidateId("CAND002");
        c2.setFirstName("John");

        candidateRepository.save(c1);
        candidateRepository.save(c2);

        List<Candidate> all = candidateRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void testDeleteById() {
        Candidate candidate = new Candidate();
        candidate.setCandidateId("CAND001");
        candidate.setFirstName("Jane");
        candidateRepository.save(candidate);

        candidateRepository.deleteById("CAND001");

        Optional<Candidate> found = candidateRepository.findById("CAND001");
        assertThat(found).isEmpty();
    }

    @Test
    void testSelfReferentialForeignKey() {
        Candidate parent = new Candidate();
        parent.setCandidateId("CAND001");
        parent.setFirstName("Parent");
        candidateRepository.save(parent);

        Candidate child = new Candidate();
        child.setCandidateId("CAND002");
        child.setFirstName("Child");
        child.setCandidatesCandidateId("CAND001");
        candidateRepository.save(child);

        Optional<Candidate> found = candidateRepository.findById("CAND002");
        assertThat(found).isPresent();
        assertThat(found.get().getCandidatesCandidateId()).isEqualTo("CAND001");
    }
}
