package com.erp.dto;

public class CandidateResponse {

    private String candidateId;
    private String addressId;
    private String officeId;
    private String registrationDate;
    private String firstName;
    private String emailAddress;
    private String homePhoneNumber;
    private String candidatesCandidateId;

    public CandidateResponse() {}

    public CandidateResponse(String candidateId, String addressId, String officeId, String registrationDate,
                             String firstName, String emailAddress, String homePhoneNumber, String candidatesCandidateId) {
        this.candidateId = candidateId;
        this.addressId = addressId;
        this.officeId = officeId;
        this.registrationDate = registrationDate;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.homePhoneNumber = homePhoneNumber;
        this.candidatesCandidateId = candidatesCandidateId;
    }

    public String getCandidateId() { return candidateId; }
    public void setCandidateId(String candidateId) { this.candidateId = candidateId; }

    public String getAddressId() { return addressId; }
    public void setAddressId(String addressId) { this.addressId = addressId; }

    public String getOfficeId() { return officeId; }
    public void setOfficeId(String officeId) { this.officeId = officeId; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getHomePhoneNumber() { return homePhoneNumber; }
    public void setHomePhoneNumber(String homePhoneNumber) { this.homePhoneNumber = homePhoneNumber; }

    public String getCandidatesCandidateId() { return candidatesCandidateId; }
    public void setCandidatesCandidateId(String candidatesCandidateId) { this.candidatesCandidateId = candidatesCandidateId; }
}
