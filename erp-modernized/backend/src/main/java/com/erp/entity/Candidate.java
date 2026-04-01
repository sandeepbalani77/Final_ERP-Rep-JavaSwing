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
@Table(name = "CANDIDATES")
public class Candidate {

    @Id
    @Column(name = "CANDIDATE_ID")
    private String candidateId;

    @Column(name = "ADDRESS_ID")
    private String addressId;

    @Column(name = "OFFICE_ID")
    private String officeId;

    @Column(name = "REGISTRATION_DATE")
    private String registrationDate;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "HOME_PHONE_NUMBER")
    private String homePhoneNumber;

    @Column(name = "CANDIDATES_CANDIDATE_ID")
    private String candidatesCandidateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OFFICE_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANDIDATES_CANDIDATE_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Candidate parentCandidate;

    public Candidate() {}

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

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public Office getOffice() { return office; }
    public void setOffice(Office office) { this.office = office; }

    public Candidate getParentCandidate() { return parentCandidate; }
    public void setParentCandidate(Candidate parentCandidate) { this.parentCandidate = parentCandidate; }
}
