package com.erp.dto;

import jakarta.validation.constraints.NotBlank;

public class VacancyRequest {

    @NotBlank(message = "Vacancy ID is required")
    private String vacancyId;

    private String clientId;
    private String dateVacancyPublishzhed;
    private String dateVacancyFilled;
    private String feePayable;
    private String dateFeePaid;
    private String vacanciesVacancyId;

    public VacancyRequest() {}

    public String getVacancyId() { return vacancyId; }
    public void setVacancyId(String vacancyId) { this.vacancyId = vacancyId; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getDateVacancyPublishzhed() { return dateVacancyPublishzhed; }
    public void setDateVacancyPublishzhed(String dateVacancyPublishzhed) { this.dateVacancyPublishzhed = dateVacancyPublishzhed; }

    public String getDateVacancyFilled() { return dateVacancyFilled; }
    public void setDateVacancyFilled(String dateVacancyFilled) { this.dateVacancyFilled = dateVacancyFilled; }

    public String getFeePayable() { return feePayable; }
    public void setFeePayable(String feePayable) { this.feePayable = feePayable; }

    public String getDateFeePaid() { return dateFeePaid; }
    public void setDateFeePaid(String dateFeePaid) { this.dateFeePaid = dateFeePaid; }

    public String getVacanciesVacancyId() { return vacanciesVacancyId; }
    public void setVacanciesVacancyId(String vacanciesVacancyId) { this.vacanciesVacancyId = vacanciesVacancyId; }
}
