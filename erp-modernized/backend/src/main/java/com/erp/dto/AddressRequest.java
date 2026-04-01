package com.erp.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressRequest {

    @NotBlank(message = "Address ID is required")
    private String addressId;

    private String cityTown;
    private String stateCountryProvince;
    private String zip;
    private String country;

    public AddressRequest() {}

    public String getAddressId() { return addressId; }
    public void setAddressId(String addressId) { this.addressId = addressId; }

    public String getCityTown() { return cityTown; }
    public void setCityTown(String cityTown) { this.cityTown = cityTown; }

    public String getStateCountryProvince() { return stateCountryProvince; }
    public void setStateCountryProvince(String stateCountryProvince) { this.stateCountryProvince = stateCountryProvince; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
