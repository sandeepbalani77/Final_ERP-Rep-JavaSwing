package com.erp.dto;

public class VacancyResponse {

    private String vacancyId;
    private String clientId;
    private String dateVacancyPublishzhed;
    private String dateVacancyFilled;
    private String feePayable;
    private String dateFeePaid;
    private String vacanciesVacancyId;

    public VacancyResponse() {}

    public VacancyResponse(String vacancyId, String clientId, String dateVacancyPublishzhed, String dateVacancyFilled,
                           String feePayable, String dateFeePaid, String vacanciesVacancyId) {
        this.vacancyId = vacancyId;
        this.clientId = clientId;
        this.dateVacancyPublishzhed = dateVacancyPublishzhed;
        this.dateVacancyFilled = dateVacancyFilled;
        this.feePayable = feePayable;
        this.dateFeePaid = dateFeePaid;
        this.vacanciesVacancyId = vacanciesVacancyId;
    }

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
