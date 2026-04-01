package com.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.dto.AddressRequest;
import com.erp.dto.AddressResponse;
import com.erp.entity.Address;
import com.erp.exception.EntityNotFoundException;
import com.erp.repository.AddressRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressResponse> findAll() {
        return addressRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public AddressResponse findById(String addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + addressId));
        return toResponse(address);
    }

    public AddressResponse create(AddressRequest request) {
        Address address = toEntity(request);
        return toResponse(addressRepository.save(address));
    }

    public AddressResponse update(String addressId, AddressRequest request) {
        if (!addressRepository.existsById(addressId)) {
            throw new EntityNotFoundException("Address not found: " + addressId);
        }
        Address address = toEntity(request);
        address.setAddressId(addressId);
        return toResponse(addressRepository.save(address));
    }

    public void delete(String addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new EntityNotFoundException("Address not found: " + addressId);
        }
        addressRepository.deleteById(addressId);
    }

    private Address toEntity(AddressRequest request) {
        Address address = new Address();
        address.setAddressId(request.getAddressId());
        address.setCityTown(request.getCityTown());
        address.setStateCountryProvince(request.getStateCountryProvince());
        address.setZip(request.getZip());
        address.setCountry(request.getCountry());
        return address;
    }

    private AddressResponse toResponse(Address address) {
        return new AddressResponse(
                address.getAddressId(),
                address.getCityTown(),
                address.getStateCountryProvince(),
                address.getZip(),
                address.getCountry()
        );
    }
}
