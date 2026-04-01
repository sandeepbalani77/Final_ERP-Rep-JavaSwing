package com.erp.dto;

public class ClientResponse {

    private String clientName;
    private String clientId;
    private String clientName1;
    private String emailAddress;
    private String cellMobilePhoneNumber;
    private String dateFirstContact;
    private String addressId;
    private String officeId;
    private String clientsClientId;

    public ClientResponse() {}

    public ClientResponse(String clientName, String clientId, String clientName1, String emailAddress,
                          String cellMobilePhoneNumber, String dateFirstContact, String addressId,
                          String officeId, String clientsClientId) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.clientName1 = clientName1;
        this.emailAddress = emailAddress;
        this.cellMobilePhoneNumber = cellMobilePhoneNumber;
        this.dateFirstContact = dateFirstContact;
        this.addressId = addressId;
        this.officeId = officeId;
        this.clientsClientId = clientsClientId;
    }

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
}
