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
import com.erp.entity.Office;
import com.erp.entity.RecruitmentAgency;

@DataJpaTest
@ActiveProfiles("test")
class OfficeRepositoryTest {

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private AddressRepository addressRepository;

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
    }

    @Test
    void testSaveAndFindById() {
        Office office = new Office();
        office.setOfficeId("OFF001");
        office.setOfficeName("Main Office");
        office.setOfficeDetails("Headquarters");
        office.setAddressId("ADDR001");
        office.setAgencyId("AG001");

        officeRepository.save(office);

        Optional<Office> found = officeRepository.findById("OFF001");
        assertThat(found).isPresent();
        assertThat(found.get().getOfficeName()).isEqualTo("Main Office");
        assertThat(found.get().getAddressId()).isEqualTo("ADDR001");
    }

    @Test
    void testFindAll() {
        Office off1 = new Office();
        off1.setOfficeId("OFF001");
        off1.setOfficeName("Office One");
        off1.setAddressId("ADDR001");
        off1.setAgencyId("AG001");

        Office off2 = new Office();
        off2.setOfficeId("OFF002");
        off2.setOfficeName("Office Two");

        officeRepository.save(off1);
        officeRepository.save(off2);

        List<Office> all = officeRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void testDeleteById() {
        Office office = new Office();
        office.setOfficeId("OFF001");
        office.setOfficeName("Main Office");
        officeRepository.save(office);

        officeRepository.deleteById("OFF001");

        Optional<Office> found = officeRepository.findById("OFF001");
        assertThat(found).isEmpty();
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Office> found = officeRepository.findById("NONEXISTENT");
        assertThat(found).isEmpty();
    }
}
