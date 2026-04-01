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

import com.erp.dto.AddressRequest;
import com.erp.dto.AddressResponse;
import com.erp.entity.Address;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.AddressRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address createAddress(String id, String city, String country) {
        Address address = new Address();
        address.setAddressId(id);
        address.setCityTown(city);
        address.setStateCountryProvince("State");
        address.setZip("12345");
        address.setCountry(country);
        return address;
    }

    private AddressRequest createRequest(String id) {
        AddressRequest request = new AddressRequest();
        request.setAddressId(id);
        request.setCityTown("New York");
        request.setStateCountryProvince("NY");
        request.setZip("10001");
        request.setCountry("USA");
        return request;
    }

    @Test
    void findAll_returnsAllAddresses() {
        when(addressRepository.findAll()).thenReturn(List.of(
                createAddress("ADDR001", "New York", "USA"),
                createAddress("ADDR002", "London", "UK")
        ));

        List<AddressResponse> result = addressService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getAddressId()).isEqualTo("ADDR001");
    }

    @Test
    void findById_success() {
        when(addressRepository.findById("ADDR001")).thenReturn(Optional.of(createAddress("ADDR001", "New York", "USA")));

        AddressResponse result = addressService.findById("ADDR001");

        assertThat(result.getCityTown()).isEqualTo("New York");
    }

    @Test
    void findById_notFound_throwsException() {
        when(addressRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.findById("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Address not found");
    }

    @Test
    void create_savesAndReturnsResponse() {
        Address saved = createAddress("ADDR001", "New York", "USA");
        when(addressRepository.save(any(Address.class))).thenReturn(saved);

        AddressResponse result = addressService.create(createRequest("ADDR001"));

        assertThat(result.getAddressId()).isEqualTo("ADDR001");
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void update_success() {
        when(addressRepository.existsById("ADDR001")).thenReturn(true);
        Address saved = createAddress("ADDR001", "New York", "USA");
        when(addressRepository.save(any(Address.class))).thenReturn(saved);

        AddressResponse result = addressService.update("ADDR001", createRequest("ADDR001"));

        assertThat(result.getAddressId()).isEqualTo("ADDR001");
    }

    @Test
    void update_notFound_throwsException() {
        when(addressRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> addressService.update("NONEXISTENT", createRequest("NONEXISTENT")))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(addressRepository.existsById("ADDR001")).thenReturn(true);

        addressService.delete("ADDR001");

        verify(addressRepository).deleteById("ADDR001");
    }

    @Test
    void delete_notFound_throwsException() {
        when(addressRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> addressService.delete("NONEXISTENT"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
