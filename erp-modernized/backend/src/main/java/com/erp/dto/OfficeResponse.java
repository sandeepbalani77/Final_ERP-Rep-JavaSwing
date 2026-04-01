package com.erp.dto;

public class OfficeResponse {

    private String officeId;
    private String officeName;
    private String officeDetails;
    private String addressId;
    private String agencyId;

    public OfficeResponse() {}

    public OfficeResponse(String officeId, String officeName, String officeDetails, String addressId, String agencyId) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.officeDetails = officeDetails;
        this.addressId = addressId;
        this.agencyId = agencyId;
    }

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
}
