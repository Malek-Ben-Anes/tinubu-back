package com.tinubu.service;


import com.tinubu.dto.InsurancePolicyDto;
import com.tinubu.dto.InsurancePolicySaveDto;
import com.tinubu.entity.InsurancePolicyEntity;
import com.tinubu.mapper.InsurancePolicyMapper;
import com.tinubu.repository.InsurancePolicyRepository;
import com.tinubu.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class InsurancePolicyService {

    private final InsurancePolicyRepository repository;
    private final InsurancePolicyMapper mapper;
    private final Clock clock;

    public Page<InsurancePolicyDto> findAll(Pageable pageable) {
        Page<InsurancePolicyEntity> entityPage = repository.findAll(pageable);
        return entityPage.map(mapper::toDto);
    }

    public InsurancePolicyDto findById(Long id) {
        InsurancePolicyEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found by id: " + id));

        return mapper.toDto(entity);
    }

    public InsurancePolicyDto createInsurancePolicy(InsurancePolicySaveDto dto) {
        InsurancePolicyEntity policy = mapper.toEntity(dto);
        policy.setCreatedAt(LocalDateTime.now(clock));
        policy.setUpdatedAt(LocalDateTime.now(clock));
        return mapper.toDto(repository.save(policy));
    }

    @Transactional
    public InsurancePolicyDto updatePolicy(Long id, InsurancePolicySaveDto dto) {
        InsurancePolicyEntity policy = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found by id: " + id));

        policy.setName(dto.getName());
        policy.setStatus(dto.getStatus());
        policy.setCoverageStartDate(dto.getCoverageStartDate());
        policy.setCoverageEndDate(dto.getCoverageEndDate());
        policy.setUpdatedAt(LocalDateTime.now(clock));

        return mapper.toDto(repository.save(policy));
    }
}
