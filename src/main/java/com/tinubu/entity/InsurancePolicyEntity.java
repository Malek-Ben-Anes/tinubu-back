package com.tinubu.entity;
import com.tinubu.enums.InsurancePolicyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "insurance_policy")
public class InsurancePolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InsurancePolicyStatus status;

    @NotNull
    @Column(name = "coverage_start_date")
    private LocalDateTime coverageStartDate;

    @NotNull
    @Column(name = "coverage_end_date")
    private LocalDateTime coverageEndDate;

    @NotNull
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
