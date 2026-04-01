package com.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RECRUITMENT_AGENCIES")
public class RecruitmentAgency {

    @Id
    @Column(name = "AGENCY_ID")
    private String agencyId;

    @Column(name = "AGECNY_NAME")
    private String agecnyName;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_GENDER_MF")
    private String contactGenderMf;

    @Column(name = "CONTACT_JOB_TITLE")
    private String contactJobTitle;

    @Column(name = "CELL_MOBILE_PHONE")
    private String cellMobilePhone;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "WEB_ADDRESS")
    private String webAddress;

    public RecruitmentAgency() {}

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
