package com.erp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.RecruitmentAgencyRequest;
import com.erp.dto.RecruitmentAgencyResponse;
import com.erp.service.RecruitmentAgencyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agencies")
public class RecruitmentAgencyController {

    private final RecruitmentAgencyService recruitmentAgencyService;

    public RecruitmentAgencyController(RecruitmentAgencyService recruitmentAgencyService) {
        this.recruitmentAgencyService = recruitmentAgencyService;
    }

    @GetMapping
    public ResponseEntity<List<RecruitmentAgencyResponse>> findAll() {
        return ResponseEntity.ok(recruitmentAgencyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentAgencyResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(recruitmentAgencyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RecruitmentAgencyResponse> create(@Valid @RequestBody RecruitmentAgencyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recruitmentAgencyService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruitmentAgencyResponse> update(@PathVariable String id, @Valid @RequestBody RecruitmentAgencyRequest request) {
        return ResponseEntity.ok(recruitmentAgencyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        recruitmentAgencyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
