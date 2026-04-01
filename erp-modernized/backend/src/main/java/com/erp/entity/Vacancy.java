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
@Table(name = "VACANCIES")
public class Vacancy {

    @Id
    @Column(name = "VACANCY_ID")
    private String vacancyId;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "DATE_VACANCY_PUBLISHZHED")
    private String dateVacancyPublishzhed;

    @Column(name = "DATE_VACANCY_FILLED")
    private String dateVacancyFilled;

    @Column(name = "FEE_PAYABLE")
    private String feePayable;

    @Column(name = "DATE_FEE_PAID")
    private String dateFeePaid;

    @Column(name = "VACANCIES_VACANCY_ID")
    private String vacanciesVacancyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VACANCIES_VACANCY_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Vacancy parentVacancy;

    public Vacancy() {}

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

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Vacancy getParentVacancy() { return parentVacancy; }
    public void setParentVacancy(Vacancy parentVacancy) { this.parentVacancy = parentVacancy; }
}
