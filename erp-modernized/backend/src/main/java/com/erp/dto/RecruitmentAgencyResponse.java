package com.erp.dto;

public class RecruitmentAgencyResponse {

    private String agencyId;
    private String agecnyName;
    private String contactName;
    private String contactGenderMf;
    private String contactJobTitle;
    private String cellMobilePhone;
    private String emailAddress;
    private String webAddress;

    public RecruitmentAgencyResponse() {}

    public RecruitmentAgencyResponse(String agencyId, String agecnyName, String contactName, String contactGenderMf,
                                     String contactJobTitle, String cellMobilePhone, String emailAddress, String webAddress) {
        this.agencyId = agencyId;
        this.agecnyName = agecnyName;
        this.contactName = contactName;
        this.contactGenderMf = contactGenderMf;
        this.contactJobTitle = contactJobTitle;
        this.cellMobilePhone = cellMobilePhone;
        this.emailAddress = emailAddress;
        this.webAddress = webAddress;
    }

    public String getAgencyId() { return agencyId; }
    public void setAgencyId(String agencyId) { this.agencyId = agencyId; }

    public String getAgecnyName() { return agecnyName; }
    public void setAgecnyName(String agecnyName) { this.agecnyName = agecnyName; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactGenderMf() { return contactGenderMf; }
    public void setContactGenderMf(String contactGenderMf) { this.contactGenderMf = contactGenderMf; }

    public String getContactJobTitle() { return contactJobTitle; }
    public void setContactJobTitle(String contactJobTitle) { this.contactJobTitle = contactJobTitle; }

    public String getCellMobilePhone() { return cellMobilePhone; }
    public void setCellMobilePhone(String cellMobilePhone) { this.cellMobilePhone = cellMobilePhone; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getWebAddress() { return webAddress; }
    public void setWebAddress(String webAddress) { this.webAddress = webAddress; }
}
