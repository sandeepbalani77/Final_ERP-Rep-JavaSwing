package com.erp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OFFICES")
public class Office {

    @Id
    @Column(name = "OFFICE_ID")
    private String officeId;

    @Column(name = "OFFICE_NAME")
    private String officeName;

    @Column(name = "OFFICE_DETAILS")
    private String officeDetails;

    @Column(name = "ADDRESS_ID")
    private String addressId;

    @Column(name = "AGENCY_ID")
    private String agencyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGENCY_ID", insertable = false, updatable = false)
    @JsonIgnore
    private RecruitmentAgency agency;

    public Office() {}

    public String getOfficeId() { return officeId; }
    public void setOfficeId(String officeId) { this.officeId = officeId; }

    public String getOfficeName() { return officeName; }
    public void setOfficeName(String officeName) { this.officeName = officeName; }

    public String getOfficeDetails() { return officeDetails; }
    public void setOfficeDetails(String officeDetails) { this.officeDetails = officeDetails; }

    public String getAddressId() { return addressId; }
    public void setAddressId(String addressId) { this.addressId = addressId; }

    public String getAgencyId() { return agencyId; }
    public void setAgencyId(String agencyId) { this.agencyId = agencyId; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public RecruitmentAgency getAgency() { return agency; }
    public void setAgency(RecruitmentAgency agency) { this.agency = agency; }
}
