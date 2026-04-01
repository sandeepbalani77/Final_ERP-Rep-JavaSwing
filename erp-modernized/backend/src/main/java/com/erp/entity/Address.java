package com.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @Column(name = "ADDRESS_ID")
    private String addressId;

    @Column(name = "CITY_TOWN")
    private String cityTown;

    @Column(name = "STATE_COUNTRY_PROVINCE")
    private String stateCountryProvince;

    @Column(name = "ZIP")
    private String zip;

    @Column(name = "COUNTRY")
    private String country;

    public Address() {}

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
