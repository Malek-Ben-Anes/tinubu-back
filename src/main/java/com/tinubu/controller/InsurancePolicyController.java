package com.tinubu.controller;

import com.tinubu.dto.InsurancePolicyDto;
import com.tinubu.dto.InsurancePolicySaveDto;
import com.tinubu.service.InsurancePolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/policies")
@Validated
public class InsurancePolicyController {

    private final InsurancePolicyService service;

    // Get all policies
    @GetMapping
    public ResponseEntity<Page<InsurancePolicyDto>> listPolicies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("id")));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    // Get a single policy by ID
    @GetMapping("/{id}")
    public ResponseEntity<InsurancePolicyDto> getPolicy(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // Create a new policy
    @PostMapping
    public ResponseEntity<InsurancePolicyDto> createPolicy(@RequestBody @Valid InsurancePolicySaveDto saveDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createInsurancePolicy(saveDto));
    }

    // Update a policy
    @PutMapping("/{id}")
    public ResponseEntity<InsurancePolicyDto> updatePolicy(@PathVariable Long id,
                                                           @RequestBody @Valid InsurancePolicySaveDto saveDto) {
        return ResponseEntity.ok(service.updatePolicy(id, saveDto));
    }
}
