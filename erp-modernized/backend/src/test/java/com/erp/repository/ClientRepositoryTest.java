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
import com.erp.entity.Client;
import com.erp.entity.Office;
import com.erp.entity.RecruitmentAgency;

@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

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
        Client client = new Client();
        client.setClientId("CL001");
        client.setClientName("Acme Corp");
        client.setClientName1("Acme");
        client.setEmailAddress("acme@test.com");
        client.setCellMobilePhoneNumber("1234567890");
        client.setDateFirstContact("2024-01-01");
        client.setAddressId("ADDR001");
        client.setOfficeId("OFF001");

        clientRepository.save(client);

        Optional<Client> found = clientRepository.findById("CL001");
        assertThat(found).isPresent();
        assertThat(found.get().getClientName()).isEqualTo("Acme Corp");
        assertThat(found.get().getAddressId()).isEqualTo("ADDR001");
    }

    @Test
    void testFindAll() {
        Client cl1 = new Client();
        cl1.setClientId("CL001");
        cl1.setClientName("Client One");

        Client cl2 = new Client();
        cl2.setClientId("CL002");
        cl2.setClientName("Client Two");

        clientRepository.save(cl1);
        clientRepository.save(cl2);

        List<Client> all = clientRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void testDeleteById() {
        Client client = new Client();
        client.setClientId("CL001");
        client.setClientName("Acme Corp");
        clientRepository.save(client);

        clientRepository.deleteById("CL001");

        Optional<Client> found = clientRepository.findById("CL001");
        assertThat(found).isEmpty();
    }

    @Test
    void testSelfReferentialForeignKey() {
        Client parent = new Client();
        parent.setClientId("CL001");
        parent.setClientName("Parent Corp");
        clientRepository.save(parent);

        Client child = new Client();
        child.setClientId("CL002");
        child.setClientName("Child Corp");
        child.setClientsClientId("CL001");
        clientRepository.save(child);

        Optional<Client> found = clientRepository.findById("CL002");
        assertThat(found).isPresent();
        assertThat(found.get().getClientsClientId()).isEqualTo("CL001");
    }
}
