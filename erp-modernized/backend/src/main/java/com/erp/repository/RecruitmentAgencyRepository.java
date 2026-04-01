package com.erp.repository;

import com.erp.entity.RecruitmentAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentAgencyRepository extends JpaRepository<RecruitmentAgency, String> {
}
