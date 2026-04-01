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
@Table(name = "CLIENTS")
public class Client {

    @Column(name = "CLIENT_NAME")
    private String clientName;

    @Id
    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "CLIENT_NAME1")
    private String clientName1;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "CELL_MOBILE_PHONE_NUMBER")
    private String cellMobilePhoneNumber;

    @Column(name = "DATE_FIRST_CONTACT")
    private String dateFirstContact;

    @Column(name = "ADDRESS_ID")
    private String addressId;

    @Column(name = "OFFICE_ID")
    private String officeId;

    @Column(name = "CLIENTS_CLIENT_ID")
    private String clientsClientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OFFICE_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTS_CLIENT_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Client parentClient;

    public Client() {}

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientName1() { return clientName1; }
    public void setClientName1(String clientName1) { this.clientName1 = clientName1; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getCellMobilePhoneNumber() { return cellMobilePhoneNumber; }
    public void setCellMobilePhoneNumber(String cellMobilePhoneNumber) { this.cellMobilePhoneNumber = cellMobilePhoneNumber; }

    public String getDateFirstContact() { return dateFirstContact; }
    public void setDateFirstContact(String dateFirstContact) { this.dateFirstContact = dateFirstContact; }

    public String getAddressId() { return addressId; }
    public void setAddressId(String addressId) { this.addressId = addressId; }

    public String getOfficeId() { return officeId; }
    public void setOfficeId(String officeId) { this.officeId = officeId; }

    public String getClientsClientId() { return clientsClientId; }
    public void setClientsClientId(String clientsClientId) { this.clientsClientId = clientsClientId; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public Office getOffice() { return office; }
    public void setOffice(Office office) { this.office = office; }

    public Client getParentClient() { return parentClient; }
    public void setParentClient(Client parentClient) { this.parentClient = parentClient; }
}
