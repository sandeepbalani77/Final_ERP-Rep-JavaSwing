package com.erp.dto;

import jakarta.validation.constraints.NotBlank;

public class RecruitmentAgencyRequest {

    @NotBlank(message = "Agency ID is required")
    private String agencyId;

    private String agecnyName;
    private String contactName;
    private String contactGenderMf;
    private String contactJobTitle;
    private String cellMobilePhone;
    private String emailAddress;
    private String webAddress;

    public RecruitmentAgencyRequest() {}

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
