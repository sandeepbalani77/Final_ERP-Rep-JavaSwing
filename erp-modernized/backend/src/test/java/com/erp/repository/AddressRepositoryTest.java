package com.erp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.erp.entity.Address;

@DataJpaTest
@ActiveProfiles("test")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void testSaveAndFindById() {
        Address address = new Address();
        address.setAddressId("ADDR001");
        address.setCityTown("New York");
        address.setStateCountryProvince("NY");
        address.setZip("10001");
        address.setCountry("USA");

        addressRepository.save(address);

        Optional<Address> found = addressRepository.findById("ADDR001");
        assertThat(found).isPresent();
        assertThat(found.get().getCityTown()).isEqualTo("New York");
        assertThat(found.get().getCountry()).isEqualTo("USA");
    }

    @Test
    void testFindAll() {
        Address addr1 = new Address();
        addr1.setAddressId("ADDR001");
        addr1.setCityTown("New York");
        addr1.setCountry("USA");

        Address addr2 = new Address();
        addr2.setAddressId("ADDR002");
        addr2.setCityTown("London");
        addr2.setCountry("UK");

        addressRepository.save(addr1);
        addressRepository.save(addr2);

        List<Address> all = addressRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void testDeleteById() {
        Address address = new Address();
        address.setAddressId("ADDR001");
        address.setCityTown("New York");
        addressRepository.save(address);

        addressRepository.deleteById("ADDR001");

        Optional<Address> found = addressRepository.findById("ADDR001");
        assertThat(found).isEmpty();
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Address> found = addressRepository.findById("NONEXISTENT");
        assertThat(found).isEmpty();
    }
}
