package com.tinubu.repository;

import com.tinubu.entity.InsurancePolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicyEntity, Long> {
}
