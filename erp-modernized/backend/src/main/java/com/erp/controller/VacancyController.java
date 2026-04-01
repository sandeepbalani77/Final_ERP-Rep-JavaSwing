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

import com.erp.dto.VacancyRequest;
import com.erp.dto.VacancyResponse;
import com.erp.service.VacancyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping
    public ResponseEntity<List<VacancyResponse>> findAll() {
        return ResponseEntity.ok(vacancyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacancyResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(vacancyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VacancyResponse> create(@Valid @RequestBody VacancyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacancyResponse> update(@PathVariable String id, @Valid @RequestBody VacancyRequest request) {
        return ResponseEntity.ok(vacancyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        vacancyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
