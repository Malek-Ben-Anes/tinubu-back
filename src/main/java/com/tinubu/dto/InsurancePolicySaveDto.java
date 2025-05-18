package com.tinubu.dto;

import com.tinubu.enums.InsurancePolicyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsurancePolicySaveDto {

    @NotBlank(message = "Policy name must not be blank")
    private String name;

    @NotNull(message = "Status is required")
    private InsurancePolicyStatus status;

    @NotNull(message = "Coverage activation date is required")
    private LocalDateTime coverageStartDate;

    @NotNull(message = "Coverage expiration date is required")
    private LocalDateTime coverageEndDate;

}
